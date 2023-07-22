package ru.kyamshanov.mission.session_front.api

import kotlinx.coroutines.flow.StateFlow
import ru.kyamshanov.mission.session_front.api.model.UserRole
import ru.kyamshanov.mission.session_front.api.session.Session

interface SessionInfo {

    val sessionState: StateFlow<Session>

    val session: Session
        get() = sessionState.value
}