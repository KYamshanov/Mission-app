package ru.kyamshanov.mission.components.point.impl.search.data.api

import io.ktor.client.request.*
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.kyamshanov.mission.components.point.impl.search.data.model.AttachedTasksResponseDto
import ru.kyamshanov.mission.core.network.api.RequestFactory
import ru.kyamshanov.mission.core.network.api.utils.retrieveBody

internal class PointApiImpl(
    private val requestFactory: RequestFactory,
) : PointApi {

    override suspend fun allProjects(): AttachedTasksResponseDto = withContext(Dispatchers.Default) {
        val response = requestFactory.get("/point/private/attached") {
            contentType(ContentType.Application.Json)
        }
        response.retrieveBody()
    }
}