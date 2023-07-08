package ru.kyamshanov.mission.foundation.impl.login.domain

interface AuthenticationInteractor {

    fun setLogin(login: String): Result<String>

    fun setPassword(password: CharSequence): Result<CharSequence>

    suspend fun onLogin(): Result<Unit>
}