package ru.kyamshanov.mission.oauth2

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.kyamshanov.mission.oauth2.api.AuthenticationInteractor
import ru.kyamshanov.mission.oauth2.api.TokensInfo


internal expect fun generateCodeVerifier(): String

internal expect fun generateCodeChallange(codeVerifier: String): String

internal class AuthenticationInteractorImpl(
    private val authenticationApi: AuthenticationApi,
) : AuthenticationInteractor {
    override fun getCodeVerifier(): String = generateCodeVerifier()

    override fun provideAuthorizationUri(codeVerifier: String, callbackPort: Int): String = buildString {
        val responseType = "code"
        val clientId = "desktop-client"
        val scope = "openid"
        val redirectUri = "http://127.0.0.1:${callbackPort}/desktop/authorized"
        val codeChallenge = generateCodeChallange(codeVerifier)

        append("http://127.0.0.1:6543/oauth2/authorize?")
        append("client_id=$clientId&")
        append("response_type=$responseType&")
        append("scope=$scope&")
        append("redirect_uri=$redirectUri&")
        append("code_challenge=$codeChallenge&")
        append("code_challenge_method=S256")
    }

    override suspend fun getToken(authorizationCode: String, codeVerifier: String, callbackPort: Int): TokensInfo =
        withContext(Dispatchers.Default) {
            authenticationApi.token(authorizationCode, codeVerifier, callbackPort).getOrThrow()
                .let { TokensInfo(it.accessToken, it.refreshToken) }
        }

    override suspend fun refresh(refreshToken: String): Result<TokensInfo> = runCatching {
        authenticationApi.token(refreshToken).let { TokensInfo(it.accessToken, it.refreshToken) }
    }

}