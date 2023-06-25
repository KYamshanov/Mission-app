package ru.kyamshanov.mission.session_front.api

import ru.kyamshanov.mission.session_front.api.model.UserRole

data class UserInfo(
    val login: String,
    val roles: List<UserRole>,
)
