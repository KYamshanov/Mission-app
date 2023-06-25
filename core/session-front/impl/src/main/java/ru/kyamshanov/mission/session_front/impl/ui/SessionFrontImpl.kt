package ru.kyamshanov.mission.session_front.impl.ui

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import ru.kyamshanov.mission.core.base.api.MissionPreferences
import ru.kyamshanov.mission.session_front.api.SessionFront
import ru.kyamshanov.mission.session_front.api.UserInfo
import ru.kyamshanov.mission.session_front.api.model.UserRole
import ru.kyamshanov.mission.session_front.api.session.JwtLoggedSession
import ru.kyamshanov.mission.session_front.api.session.LoggedSession
import ru.kyamshanov.mission.session_front.api.session.Session
import ru.kyamshanov.mission.session_front.api.session.UnauthorizedSession
import ru.kyamshanov.mission.session_front.impl.SessionInfoImpl
import ru.kyamshanov.mission.session_front.impl.domain.JwtLoginInteractor
import ru.kyamshanov.mission.session_front.impl.domain.model.AccessData
import ru.kyamshanov.mission.session_front.impl.domain.usecase.IdentifyUserUseCase
import ru.kyamshanov.mission.session_front.impl.ui.session.JwtLoggedSessionImpl
import ru.kyamshanov.mission.session_front.impl.ui.session.LoggedSessionImpl

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
        jwtLoginInteractor.login(login, password).getOrThrow().also {
            missionPreferences.saveValue(PREFERENCES_ACCESS_KEY, it.accessToken)
            missionPreferences.saveValue(PREFERENCES_REFRESH_KEY, it.refreshToken)
            missionPreferences.saveValue(PREFERENCES_SESSION_LOGIN_KEY, login)
        }.let { createJwtSession(it) }
            .also { sessionInfoImpl.session = it }
            .let { finishSetupSession(login, it) }
            .also { startAutoRefreshing() }
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
            mRefreshToken = it.refreshToken
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
        }.let { createJwtSession(it) }
            .also { sessionInfoImpl.session = it }
            .let { finishSetupSession(login, it) }
    }

    private fun createJwtSession(data: AccessData): JwtLoggedSession = JwtLoggedSessionImpl(
        accessToken = data.accessToken,
        refreshToken = data.refreshToken,
        roles = data.roles
    )

    private suspend fun finishSetupSession(login: String, jwtLoggedSession: JwtLoggedSession): LoggedSession =
        UserInfo(
            login = login,
            roles = jwtLoggedSession.roles.map { UserRole.valueOf(it) }
        )
            .let { userInfo ->
                val idToken = identifyUserUseCase.identify().getOrThrow()
                LoggedSessionImpl(
                    userInfo = userInfo,
                    idToken = idToken,
                    jwtLoggedSession = jwtLoggedSession,
                    jwtLoginInteractor = jwtLoginInteractor
                )
            }
            .also { sessionInfoImpl.session = it }

    private companion object {

        const val AUTO_REFRESHING_DELAY_MS = 5 * 60 * 1000L
    }
}