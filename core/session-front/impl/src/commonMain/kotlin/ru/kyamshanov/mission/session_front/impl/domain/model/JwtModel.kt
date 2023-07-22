package ru.kyamshanov.mission.session_front.impl.domain.model

internal data class JwtModel(
    val subject: String,
    val roles: List<String>
)
