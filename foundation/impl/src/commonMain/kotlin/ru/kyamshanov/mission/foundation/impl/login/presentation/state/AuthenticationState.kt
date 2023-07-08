package ru.kyamshanov.mission.foundation.impl.login.presentation.state

internal data class AuthenticationState(
    val login: String,
    val password: CharSequence
) {
    constructor() : this("", "")

}
