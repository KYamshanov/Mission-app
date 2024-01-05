package ru.kyamshanov.mission.oauth2

import ru.kyamshanov.mission.oauth2.data.model.TokensRsDto

internal interface AuthenticationApi {

    suspend fun token(authorizationCode: String, codeVerifier: String, callbackPort : Int): Result<TokensRsDto>

    suspend fun token(refreshToken: String): TokensRsDto

}