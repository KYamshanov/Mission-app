package ru.kyamshanov.mission.foundation.impl.login.domain

interface AuthenticationInteractor {

    suspend fun login(login: String, password: CharSequence): Result<Unit>
}