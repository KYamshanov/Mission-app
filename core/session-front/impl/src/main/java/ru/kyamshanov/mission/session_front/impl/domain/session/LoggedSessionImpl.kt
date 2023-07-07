package ru.kyamshanov.mission.session_front.impl.domain.session

import ru.kyamshanov.mission.session_front.api.model.UserInfo
import ru.kyamshanov.mission.session_front.api.model.Token
import ru.kyamshanov.mission.session_front.api.model.TokenRepository
import ru.kyamshanov.mission.session_front.api.session.LoggedSession
import ru.kyamshanov.mission.session_front.impl.domain.JwtLoginInteractor
import ru.kyamshanov.mission.session_front.impl.domain.model.AccessStatus

internal class LoggedSessionImpl(
    override val userInfo: UserInfo,
    override val tokenRepository: TokenRepository,
) : LoggedSession