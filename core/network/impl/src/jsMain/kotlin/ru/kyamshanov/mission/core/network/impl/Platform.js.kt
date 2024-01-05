package ru.kyamshanov.mission.core.network.impl

import io.ktor.client.*
import io.ktor.client.engine.js.*

actual fun httpClient(config: HttpClientConfig<*>.() -> Unit): HttpClient = HttpClient(Js)