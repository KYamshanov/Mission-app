package ru.kyamshanov.mission.session_front.api.model

data class AccessData(
    val accessToken: Token,
    val refreshToken: Token,
    val roles: List<String>,
)