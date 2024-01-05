package ru.kyamshanov.mission.core.network.impl

import io.ktor.client.*

internal expect fun httpClient(config: HttpClientConfig<*>.() -> Unit = {}): HttpClient
