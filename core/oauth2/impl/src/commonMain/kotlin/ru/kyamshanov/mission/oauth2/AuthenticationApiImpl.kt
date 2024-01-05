package ru.kyamshanov.mission.oauth2

import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.kyamshanov.mission.core.network.api.RequestFactory
import ru.kyamshanov.mission.core.network.api.utils.retrieveBody
import ru.kyamshanov.mission.oauth2.data.model.TokensRsDto

internal class AuthenticationApiImpl(
    private val requestFactory: RequestFactory
) : AuthenticationApi {

    override suspend fun token(
        authorizationCode: String,
        codeVerifier: String, callbackPort: Int
    ): Result<TokensRsDto> = runCatching {
        val response = requestFactory.post("oauth2/token") {
            contentType(ContentType.Application.FormUrlEncoded)
            setBody(
                listOf(
                    "grant_type" to "authorization_code",
                    "code" to authorizationCode,
                    "redirect_uri" to "https://127.0.0.1:$callbackPort/desktop/authorized",
                    "code_verifier" to codeVerifier
                ).formUrlEncode()
            )
            header("Authorization", "Basic ZGVza3RvcC1jbGllbnQ6c2VjcmV0")//desktop-client:secret
        }
        response.retrieveBody()
    }

    override suspend fun token(refreshToken: String): TokensRsDto = withContext(Dispatchers.Default) {
        val response = requestFactory.post("oauth2/token") {
            contentType(ContentType.Application.FormUrlEncoded)
            setBody(
                listOf(
                    "grant_type" to "refresh_token",
                    "refresh_token" to refreshToken,
                ).formUrlEncode()
            )
            header("Authorization", "Basic ZGVza3RvcC1jbGllbnQ6c2VjcmV0")//desktop-client:secret
        }
        response.retrieveBody()
    }
}