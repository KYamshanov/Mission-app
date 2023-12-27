package ru.kyamshanov.mission.core.network.impl

import io.github.aakira.napier.Napier
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import ru.kyamshanov.mission.core.di.impl.Di
import ru.kyamshanov.mission.core.network.api.RequestFactory
import ru.kyamshanov.mission.session_front.api.di.SessionFrontComponent
import ru.kyamshanov.mission.session_front.api.model.TokenRepository
import ru.kyamshanov.mission.session_front.api.session.LoggedSession

class RequestFactoryImpl(isAuthModule: Boolean = false) : RequestFactory {

    private val client = HttpClient {
        install(Logging) {
            logger = NetworkLogger()
            level = LogLevel.ALL
        }

        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                useAlternativeNames = false
            })
        }

        install(HttpTimeout) {
            requestTimeoutMillis = 120_000
            socketTimeoutMillis = 120_000
        }

        if (!isAuthModule)
            install(Auth) {
                bearer {
                    loadTokens {
                        val accessToken = getAuthorizationHeader() ?: return@loadTokens null
                        BearerTokens(accessToken, "")
                    }
                    refreshTokens {
                        try {
                            println("Refresh1")
                            val sessionFrontComponent = requireNotNull(Di.getComponent<SessionFrontComponent>())
                            val session =
                                sessionFrontComponent.sessionFront.refreshToken().getOrThrow() as LoggedSession
                            BearerTokens(session.accessToken!!.value, "")
                        } catch (e: Throwable) {
                            println("Refresh2")
                            e.printStackTrace()
                            null
                        }
                    }
                }
            }

        defaultRequest {
            url("https://www.kyamshanov.ru:3456/") //wifi
        }
    }

    override suspend fun get(endpoint: String, block: HttpRequestBuilder.() -> Unit) =
        client.get(endpoint, block)

    override suspend fun post(endpoint: String, block: HttpRequestBuilder.() -> Unit) =
        client.post(endpoint, block)

    override suspend fun delete(endpoint: String, block: HttpRequestBuilder.() -> Unit): HttpResponse =
        client.delete(endpoint, block)

    override suspend fun patch(endpoint: String, block: HttpRequestBuilder.() -> Unit): HttpResponse =
        client.patch(endpoint, block)

    private fun getAuthorizationHeader(): String? =
        (requireNotNull(Di.getComponent<SessionFrontComponent>()).sessionInfo.session as? TokenRepository)?.accessToken?.value

    private inner class NetworkLogger : Logger {

        override fun log(message: String) {
            Napier.d(message, tag = LOG_TEG)
        }
    }

    private companion object {

        const val LOG_TEG = "Network"
    }
}