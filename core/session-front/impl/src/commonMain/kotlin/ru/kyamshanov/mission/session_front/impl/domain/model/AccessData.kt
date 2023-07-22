package ru.kyamshanov.mission.session_front.impl.domain.model

internal typealias Token = String

internal data class AccessData(
    val accessToken: Token,
    val refreshToken: Token,
    val roles: List<String>,
)