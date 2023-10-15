package ru.kyamshanov.mission.components.point.impl.data.api

import io.ktor.client.request.*
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.kyamshanov.mission.components.point.impl.data.model.*
import ru.kyamshanov.mission.components.point.impl.data.model.CreateTaskRequestDto
import ru.kyamshanov.mission.components.point.impl.data.model.CreateTaskResponseDto
import ru.kyamshanov.mission.components.point.impl.data.model.TaskType
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

    override suspend fun allProjects(): AttachedTasksResponseDto = withContext(Dispatchers.Default) {
        val response = requestFactory.get("/point/private/attached") {
            contentType(ContentType.Application.Json)
        }
        response.retrieveBody()
    }

    override suspend fun create(rq: CreateTaskRequestDto): CreateTaskResponseDto = withContext(Dispatchers.Default) {
        val response = requestFactory.post("/point/private/create") {
            contentType(ContentType.Application.Json)
            setBody(rq)
        }
        response.retrieveBody()
    }

    override suspend fun delete(taskId: TaskId): Unit = withContext(Dispatchers.Default) {
        val response = requestFactory.delete("/point/private/delete") {
            contentType(ContentType.Application.Json)
            parameter("id", taskId)
        }
        response.retrieveBody()
    }

    override suspend fun setType(taskId: TaskId, taskType: TaskType): Unit = withContext(Dispatchers.Default) {
        val response = requestFactory.patch("/point/private/type") {
            contentType(ContentType.Application.Json)
            parameter("id", taskId)
            parameter("type", taskType)
        }
        response.retrieveBody()
    }
}