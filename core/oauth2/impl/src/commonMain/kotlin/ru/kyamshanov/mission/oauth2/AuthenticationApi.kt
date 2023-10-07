package ru.kyamshanov.mission.oauth2

internal interface AuthenticationApi {

    suspend fun token(authorizationCode: String, codeVerifier: String): Result<TokensRsDto>

    suspend fun token(refreshToken: String): TokensRsDto

}