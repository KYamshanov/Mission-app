package ru.kyamshanov.mission.oauth2.api

interface AuthenticationInteractor {

    fun getCodeVerifier(): String

    fun provideAuthorizationUri(codeVerifier: String, callbackPort: Int = 8080): String

    suspend fun getToken(authorizationCode: String, codeVerifier: String, callbackPort: Int = 8080): TokensInfo
    fun blockRefresh(it: String) {
        TODO("Not yet implemented")
    }

    suspend fun refresh(refreshToken: String): Result<TokensInfo>
}