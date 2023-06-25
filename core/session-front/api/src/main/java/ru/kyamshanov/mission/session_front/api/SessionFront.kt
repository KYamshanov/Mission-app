package ru.kyamshanov.mission.session_front.api

import ru.kyamshanov.mission.session_front.api.session.Session

interface SessionFront {

    suspend fun openSession(login: String, password: CharSequence): Result<Session>

    suspend fun destroySession()
}