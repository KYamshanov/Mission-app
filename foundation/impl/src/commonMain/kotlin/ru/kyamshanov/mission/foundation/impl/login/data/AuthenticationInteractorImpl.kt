package ru.kyamshanov.mission.foundation.impl.login.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.kyamshanov.mission.foundation.impl.login.domain.AuthenticationInteractor
import ru.kyamshanov.mission.session_front.api.SessionFront

internal class AuthenticationInteractorImpl(
    private val sessionFront: SessionFront
) : AuthenticationInteractor {

    private var currentLogin: String? = null
    private var currentPassword: CharSequence? = null

    override fun setLogin(login: String): Result<String> = runCatching {
        currentLogin = login
        requireNotNull(currentLogin)
    }

    override fun setPassword(password: CharSequence): Result<CharSequence> = runCatching {
        currentPassword = password
        requireNotNull(currentPassword)
    }

    override suspend fun onLogin(): Result<Unit> = runCatching {
        withContext(Dispatchers.Default) {
            val login = requireNotNull(currentLogin)
            val password = requireNotNull(currentPassword)
            sessionFront.openSession(login, password).getOrThrow()
        }
    }
}