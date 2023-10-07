package ru.kyamshanov.mission.oauth2.api

data class TokensInfo(
    val accessToken: String,
    val refreshToken: String
)
