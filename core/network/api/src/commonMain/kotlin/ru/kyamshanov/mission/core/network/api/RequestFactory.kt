package ru.kyamshanov.mission.core.network.api

import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.statement.HttpResponse

interface RequestFactory {

    suspend fun get(endpoint: String, block: HttpRequestBuilder.() -> Unit = {}): HttpResponse

    suspend fun post(endpoint: String, block: HttpRequestBuilder.() -> Unit = {}): HttpResponse
    suspend fun delete(endpoint: String, block: HttpRequestBuilder.() -> Unit): HttpResponse

    suspend fun patch(endpoint: String, block: HttpRequestBuilder.() -> Unit): HttpResponse
}