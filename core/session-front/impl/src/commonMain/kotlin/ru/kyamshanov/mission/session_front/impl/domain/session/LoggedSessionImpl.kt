package ru.kyamshanov.mission.session_front.impl.domain.session

import ru.kyamshanov.mission.session_front.api.model.UserInfo
import ru.kyamshanov.mission.session_front.api.model.Token
import ru.kyamshanov.mission.session_front.api.session.LoggedSession

internal class LoggedSessionImpl(
    override val userInfo: UserInfo,
    override val accessToken: Token,
    override val refreshToken: Token,
) : LoggedSession