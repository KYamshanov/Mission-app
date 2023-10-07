package ru.kyamshanov.mission.oauth2.api

interface AuthenticationInteractor {

    fun getCodeVerifier(): String

    fun provideAuthorizationUri(codeVerifier: String): String

    suspend fun getToken(authorizationCode: String, codeVerifier: String): TokensInfo
    fun blockRefresh(it: String) {
        TODO("Not yet implemented")
    }

    suspend fun refresh(refreshToken: String): Result<TokensInfo>
}