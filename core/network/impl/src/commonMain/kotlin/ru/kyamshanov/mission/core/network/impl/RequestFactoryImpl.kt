package ru.kyamshanov.mission.core.network.impl

import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import ru.kyamshanov.mission.core.di.impl.Di
import ru.kyamshanov.mission.core.network.api.RequestFactory
import ru.kyamshanov.mission.session_front.api.di.SessionFrontComponent
import ru.kyamshanov.mission.session_front.api.model.TokenRepository

class RequestFactoryImpl : RequestFactory {

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

        defaultRequest {
            url("http://localhost:3456/") //mobile internet
            // url("http://10.2.15.91:80/") //wifi
            getAuthorizationHeader()?.let { header(HttpHeaders.Authorization, "Bearer $it") }
        }
    }

    override suspend fun get(endpoint: String, block: HttpRequestBuilder.() -> Unit) =
        client.get(endpoint, block)

    override suspend fun post(endpoint: String, block: HttpRequestBuilder.() -> Unit) =
        client.post(endpoint, block)

    override suspend fun delete(endpoint: String, block: HttpRequestBuilder.() -> Unit): HttpResponse =
        client.delete(endpoint, block)

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