package ru.kyamshanov.mission.oauth2.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokensRqDto(
    @SerialName("grant_type")
    val grant_type: String,
    @SerialName("code")
    val code: String,
    @SerialName("redirect_uri")
    val redirect_uri: String,
    @SerialName("code_verifier")
    val code_verifier: String,
)