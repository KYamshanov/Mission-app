package ru.kyamshanov.mission.session_front.api

import ru.kyamshanov.mission.session_front.api.model.AccessData
import ru.kyamshanov.mission.session_front.api.model.TokenRepository
import ru.kyamshanov.mission.session_front.api.session.LoggedSession
import ru.kyamshanov.mission.session_front.api.session.Session

interface SessionFront {

    suspend fun openSession(accessData: AccessData): Result<Session>

    suspend fun refreshToken(): Result<Session>

    suspend fun destroySession()
}