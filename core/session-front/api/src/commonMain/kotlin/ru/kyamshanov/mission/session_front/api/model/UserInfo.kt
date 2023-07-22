package ru.kyamshanov.mission.session_front.api.model

data class UserInfo(
    val login: String,
    val roles: List<UserRole>,
)
