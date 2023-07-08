package ru.kyamshanov.mission.session_front.impl.domain.session

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import ru.kyamshanov.mission.core.base.api.MissionPreferences
import ru.kyamshanov.mission.session_front.api.SessionFront
import ru.kyamshanov.mission.session_front.api.model.Token
import ru.kyamshanov.mission.session_front.api.model.TokenRepository
import ru.kyamshanov.mission.session_front.api.model.UserInfo
import ru.kyamshanov.mission.session_front.api.model.UserRole
import ru.kyamshanov.mission.session_front.api.session.LoggedSession
import ru.kyamshanov.mission.session_front.api.session.Session
import ru.kyamshanov.mission.session_front.api.session.UnauthorizedSession
import ru.kyamshanov.mission.session_front.impl.domain.JwtLoginInteractor
import ru.kyamshanov.mission.session_front.impl.domain.model.AccessData
import ru.kyamshanov.mission.session_front.impl.domain.model.PREFERENCES_ACCESS_KEY
import ru.kyamshanov.mission.session_front.impl.domain.model.PREFERENCES_REFRESH_KEY
import ru.kyamshanov.mission.session_front.impl.domain.model.PREFERENCES_SESSION_LOGIN_KEY
import ru.kyamshanov.mission.session_front.impl.domain.usecase.IdentifyUserUseCase

internal class SessionFrontImpl constructor(
    private val jwtLoginInteractor: JwtLoginInteractor,
    private val missionPreferences: MissionPreferences,
    private val sessionInfoImpl: SessionInfoImpl,
    private val identifyUserUseCase: IdentifyUserUseCase,
) : SessionFront {

    private var sessionLifecycleScope: CoroutineScope? = null
        set(value) {
            field?.cancel()
            field = value
        }

    init {
        val scope = CoroutineScope(Job())
        //TODO Легаси. Переисать
        scope.launch {
            refreshSession()
                .onSuccess {
                    startAutoRefreshing()
                }
                .onFailure { makeUnauthorizedSession(it) }
            scope.cancel()
        }
    }

    override suspend fun openSession(login: String, password: CharSequence): Result<Session> = runCatching {
        sessionInfoImpl.session = LoggingSessionImpl()
        jwtLoginInteractor.login(login, password).getOrThrow().also {
            missionPreferences.saveValue(PREFERENCES_ACCESS_KEY, it.accessToken)
            missionPreferences.saveValue(PREFERENCES_REFRESH_KEY, it.refreshToken)
            missionPreferences.saveValue(PREFERENCES_SESSION_LOGIN_KEY, login)
        }.let { createSession(login, it) }
            .also { sessionInfoImpl.session = it }
            .also { startAutoRefreshing() }
    }.onFailure {
        it.printStackTrace()
    }

    override suspend fun destroySession() {
        breakSession()

        missionPreferences.remove(PREFERENCES_ACCESS_KEY)
        missionPreferences.remove(PREFERENCES_REFRESH_KEY)
        missionPreferences.remove(PREFERENCES_SESSION_LOGIN_KEY)
    }

    private suspend fun breakSession() {
        sessionLifecycleScope = null
        makeUnauthorizedSession(IllegalStateException("The session is destroyed"))

        missionPreferences.getValue(PREFERENCES_REFRESH_KEY)
            ?.let { jwtLoginInteractor.blockRefresh(it) }

        /*  withContext(Dispatchers.Main) {
              Di.getComponent<AuthenticationComponent>()?.launcher?.launch()
          }*/
    }

    private fun makeUnauthorizedSession(reason: Throwable) {
        sessionInfoImpl.session = UnauthorizedSession(reason)
    }

    private fun startAutoRefreshing() {
        sessionLifecycleScope = CoroutineScope(Job())

        sessionLifecycleScope?.launch {
            while (isActive) {
                delay(AUTO_REFRESHING_DELAY_MS)
                refreshSession()
                    .onFailure { breakSession() }
            }
        }
    }

    private suspend fun refreshSession(): Result<Session> = runCatching {
        var mRefreshToken: String? = null
        var mLogin: String? = null

        (sessionInfoImpl.session as? LoggedSessionImpl)?.let {
            mRefreshToken = it.refreshToken.value
            mLogin = it.userInfo.login
        } ?: run {
            mRefreshToken = missionPreferences.getValue(PREFERENCES_REFRESH_KEY)
            mLogin = missionPreferences.getValue(PREFERENCES_SESSION_LOGIN_KEY)
        }

        val refreshToken = requireNotNull(mRefreshToken) { "Refresh token has not found in shared preferences" }
        val login = requireNotNull(mLogin) { "Login has not found in shared preferences" }

        jwtLoginInteractor.refresh(refreshToken).getOrThrow().also {
            missionPreferences.saveValue(PREFERENCES_ACCESS_KEY, it.accessToken)
            missionPreferences.saveValue(PREFERENCES_REFRESH_KEY, it.refreshToken)
        }.let { createSession(login, it) }
            .also { sessionInfoImpl.session = it }
    }

    private suspend fun createSession(useLogin: String, data: AccessData): LoggedSession {
        sessionInfoImpl.session =
            LoggingSessionImpl(accessToken = Token(data.accessToken), refreshToken = Token(data.accessToken))
        val idToken = identifyUserUseCase.identify().getOrThrow()
        val userInfo = UserInfo(
            login = useLogin,
            roles = data.roles.map { UserRole.valueOf(it) }
        )
        return LoggedSessionImpl(
            userInfo = userInfo,
            idToken = idToken,
            accessToken = Token(data.accessToken),
            refreshToken = Token(data.refreshToken),
        )
    }

    private companion object {

        const val AUTO_REFRESHING_DELAY_MS = 5 * 60 * 1000L
    }
}