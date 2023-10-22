package ru.kyamshanov.mission.session_front.impl.domain.session

import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import ru.kyamshanov.mission.authorization.api.AuthorizationLauncher
import ru.kyamshanov.mission.core.base.api.MissionPreferences
import ru.kyamshanov.mission.oauth2.api.AuthenticationInteractor
import ru.kyamshanov.mission.session_front.api.SessionFront
import ru.kyamshanov.mission.session_front.api.model.AccessData
import ru.kyamshanov.mission.session_front.api.model.Token
import ru.kyamshanov.mission.session_front.api.model.UserInfo
import ru.kyamshanov.mission.session_front.api.model.UserRole
import ru.kyamshanov.mission.session_front.api.session.LoggedSession
import ru.kyamshanov.mission.session_front.api.session.Session
import ru.kyamshanov.mission.session_front.api.session.UnauthorizedSession
import ru.kyamshanov.mission.session_front.impl.domain.model.PREFERENCES_ACCESS_KEY
import ru.kyamshanov.mission.session_front.impl.domain.model.PREFERENCES_REFRESH_KEY
import ru.kyamshanov.mission.session_front.impl.domain.model.PREFERENCES_SESSION_LOGIN_KEY
import ru.kyamshanov.mission.session_front.impl.domain.usecase.IdentifyUserUseCase

internal class SessionFrontImpl constructor(
    private val missionPreferences: MissionPreferences,
    private val sessionInfoImpl: SessionInfoImpl,
/*
    private val identifyUserUseCase: IdentifyUserUseCase,
*/
    private val authenticationInteractor: AuthenticationInteractor,
    private val authorizationLauncher: AuthorizationLauncher
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
            /*  refreshSession()
                  .onFailure {
                      Napier.e(throwable = it, tag = "SessionFront") { "RefreshSession exception" }
                      makeUnauthorizedSession(it)
                  }*/
            /* refreshSession()
                 .onSuccess {
                     startAutoRefreshing()
                 }
                 .onFailure {
                     Napier.e(throwable = it, tag = "SessionFront") { "RefreshSession exception" }
                     makeUnauthorizedSession(it)
                 }*/
            makeUnauthorizedSession(NullPointerException())
            scope.cancel()
        }
    }

    override suspend fun openSession(accessData: AccessData): Result<Session> = runCatching {
        sessionInfoImpl.session = LoggingSessionImpl()
        missionPreferences.saveValue(PREFERENCES_ACCESS_KEY, accessData.accessToken.value)
        missionPreferences.saveValue(PREFERENCES_REFRESH_KEY, accessData.refreshToken.value)
        missionPreferences.saveValue(PREFERENCES_SESSION_LOGIN_KEY, "login")
        val login = "login"
        val session =
            createSession(login, accessData)
        sessionInfoImpl.session = session
        session
    }.onFailure {
        it.printStackTrace()
    }

    override suspend fun refreshToken(): Result<Session> = runCatching {
        println("Refresh")
        refreshSession().getOrElse {
            authorizationLauncher.launch()
            println("Loggin")
            sessionInfoImpl.session = LoggingSessionImpl()
            sessionInfoImpl.sessionState.first()
        }
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
            ?.let { authenticationInteractor.blockRefresh(it) }

        /*  withContext(Dispatchers.Main) {
              Di.getComponent<AuthenticationComponent>()?.launcher?.launch()
          }*/
    }

    private fun makeUnauthorizedSession(reason: Throwable) {
        sessionInfoImpl.session = UnauthorizedSession(reason)
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

        val refreshToken =
            requireNotNull(mRefreshToken) { "Refresh token has not found in shared preferences" }
        val login = requireNotNull(mLogin) { "Login has not found in shared preferences" }

        authenticationInteractor.refresh(refreshToken).getOrThrow().also {
            missionPreferences.saveValue(PREFERENCES_ACCESS_KEY, it.accessToken)
            missionPreferences.saveValue(PREFERENCES_REFRESH_KEY, it.refreshToken)
        }.let {
            createSession(
                login,
                AccessData(Token(it.accessToken), Token(it.refreshToken), emptyList())
            )
        }
            .also { sessionInfoImpl.session = it }
    }

    private suspend fun createSession(useLogin: String, data: AccessData): LoggedSession {
        sessionInfoImpl.session =
            LoggingSessionImpl(accessToken = data.accessToken, refreshToken = data.refreshToken)
        val userInfo = UserInfo(
            login = useLogin,
            roles = data.roles.map { UserRole.valueOf(it) }
        )
        return LoggedSessionImpl(
            userInfo = userInfo,
            accessToken = data.accessToken,
            refreshToken = data.refreshToken,
        )
    }

    private companion object {

        const val AUTO_REFRESHING_DELAY_MS = 5 * 60 * 1000L
    }
}