package ru.kyamshanov.mission.components.point.impl.editing.data.api

import io.ktor.client.request.*
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.kyamshanov.mission.components.point.impl.editing.data.api.PointApi
import ru.kyamshanov.mission.components.point.impl.editing.data.model.GetTaskRsDto
import ru.kyamshanov.mission.components.points.api.common.TaskId
import ru.kyamshanov.mission.core.network.api.RequestFactory
import ru.kyamshanov.mission.core.network.api.utils.retrieveBody

internal class PointApiImpl(
    private val requestFactory: RequestFactory,
) : PointApi {

    override suspend fun get(taskId: TaskId): GetTaskRsDto = withContext(Dispatchers.Default) {
        val response = requestFactory.get("/point/private/get") {
            contentType(ContentType.Application.Json)
            parameter("id", taskId)
        }
        response.retrieveBody()
    }
}