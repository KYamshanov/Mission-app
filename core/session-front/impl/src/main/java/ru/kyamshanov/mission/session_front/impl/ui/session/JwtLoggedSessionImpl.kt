package ru.kyamshanov.mission.session_front.impl.ui.session

import ru.kyamshanov.mission.session_front.api.UserInfo
import ru.kyamshanov.mission.session_front.api.session.JwtLoggedSession
import ru.kyamshanov.mission.session_front.impl.domain.JwtLoginInteractor
import ru.kyamshanov.mission.session_front.impl.domain.model.AccessStatus.ACTIVE

internal class JwtLoggedSessionImpl(
    override val accessToken: String,
    override val refreshToken: String,
    override val roles: List<String>
) : JwtLoggedSession {

    /*    override suspend fun isActive(): Boolean =
            accessToken.let { jwtLoginInteractor.check(it).getOrNull() == ACTIVE }*/
}