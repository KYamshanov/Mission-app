package ru.kyamshanov.mission.foundation.impl.login.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.kyamshanov.mission.foundation.impl.login.domain.AuthenticationInteractor
import ru.kyamshanov.mission.session_front.api.SessionFront

internal class AuthenticationInteractorImpl(
    private val sessionFront: SessionFront
) : AuthenticationInteractor {


    override suspend fun login(login: String, password: CharSequence): Result<Unit> = runCatching {
        withContext(Dispatchers.Default) {
            sessionFront.openSession(login, password).getOrThrow()
        }
    }
}