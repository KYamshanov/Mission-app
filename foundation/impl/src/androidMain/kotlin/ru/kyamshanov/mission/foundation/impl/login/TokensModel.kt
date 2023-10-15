package ru.kyamshanov.mission.foundation.impl.login

internal data class TokensModel(
    val accessToken: String,
    val refreshToken: String,
    val idToken: String
)
