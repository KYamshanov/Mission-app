package ru.kyamshanov.mission.session_front.impl.domain.session

import ru.kyamshanov.mission.session_front.api.model.Token
import ru.kyamshanov.mission.session_front.api.session.LoggingSession

internal class LoggingSessionImpl(
    override val accessToken: Token? = null,
    override val refreshToken: Token? = null
) : LoggingSession