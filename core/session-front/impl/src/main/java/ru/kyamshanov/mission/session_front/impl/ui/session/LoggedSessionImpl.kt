package ru.kyamshanov.mission.session_front.impl.ui.session

import ru.kyamshanov.mission.session_front.api.UserInfo
import ru.kyamshanov.mission.session_front.api.model.IdToken
import ru.kyamshanov.mission.session_front.api.session.JwtLoggedSession
import ru.kyamshanov.mission.session_front.api.session.LoggedSession
import ru.kyamshanov.mission.session_front.impl.domain.JwtLoginInteractor
import ru.kyamshanov.mission.session_front.impl.domain.model.AccessStatus

internal class LoggedSessionImpl(
    override val userInfo: UserInfo,
    override val idToken: IdToken,
    private val jwtLoggedSession: JwtLoggedSession,
    private val jwtLoginInteractor: JwtLoginInteractor,
) : LoggedSession, JwtLoggedSession by jwtLoggedSession {

    override suspend fun isActive(): Boolean =
        accessToken.let { jwtLoginInteractor.check(it).getOrNull() == AccessStatus.ACTIVE }
}