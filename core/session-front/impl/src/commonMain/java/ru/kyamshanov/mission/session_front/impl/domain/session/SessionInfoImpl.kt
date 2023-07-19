package ru.kyamshanov.mission.session_front.impl.domain.session

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.kyamshanov.mission.session_front.api.SessionInfo
import ru.kyamshanov.mission.session_front.api.model.UserRole
import ru.kyamshanov.mission.session_front.api.session.LoggedSession
import ru.kyamshanov.mission.session_front.api.session.Session
import ru.kyamshanov.mission.session_front.api.session.UnidentifiedSession

internal class SessionInfoImpl constructor() : SessionInfo {

    private val _sessionState = MutableStateFlow<Session>(UnidentifiedSession)

    override val sessionState: StateFlow<Session> = _sessionState.asStateFlow()

    override var session: Session
        get() = super.session
        set(value) {
            _sessionState.value = value
        }
}