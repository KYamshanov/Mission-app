package ru.kyamshanov.mission.oauth2

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokensRsDto(
    @SerialName("access_token")
    val accessToken: String,
    @SerialName("refresh_token")
    val refreshToken: String,
    val scope: String,
    @SerialName("id_token")
    val idToken: String,
    @SerialName("token_type")
    val tokenType: String,
    @SerialName("expires_in")
    val expiresIn: Long,
)