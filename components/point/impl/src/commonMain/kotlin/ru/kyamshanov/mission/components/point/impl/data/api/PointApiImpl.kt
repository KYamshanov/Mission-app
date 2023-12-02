package ru.kyamshanov.mission.components.point.impl.data.api

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.kyamshanov.mission.components.point.impl.data.model.*
import ru.kyamshanov.mission.components.point.impl.data.model.CreateTaskRequestDto
import ru.kyamshanov.mission.components.point.impl.data.model.CreateTaskResponseDto
import ru.kyamshanov.mission.components.point.impl.data.model.TaskTypeDto
import ru.kyamshanov.mission.components.points.api.common.ProjectId
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

    override suspend fun allTasks(): AttachedTasksResponseDto = withContext(Dispatchers.Default) {
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

    override suspend fun setType(taskId: TaskId, taskTypeDto: TaskTypeDto): Unit = withContext(Dispatchers.Default) {
        val response = requestFactory.patch("/point/private/type") {
            contentType(ContentType.Application.Json)
            parameter("id", taskId)
            parameter("type", taskTypeDto.toString())
        }
        response.retrieveBody()
    }

    override suspend fun setStatus(taskId: TaskId, taskStatusDto: TaskStatusDto): Unit =
        withContext(Dispatchers.Default) {
            val response = requestFactory.patch("/point/private/status") {
                contentType(ContentType.Application.Json)
                parameter("id", taskId)
                parameter("status", taskStatusDto)
            }
            response.retrieveBody()
        }

    override suspend fun setPriority(taskId: TaskId, priority: TaskPriorityDto): Unit =
        withContext(Dispatchers.Default) {
            val response = requestFactory.patch("/point/private/priority") {
                contentType(ContentType.Application.Json)
                parameter("id", taskId)
                parameter("value", priority)
            }
            response.retrieveBody()
        }

    override suspend fun removePriority(taskId: TaskId): Unit =
        withContext(Dispatchers.Default) {
            val response = requestFactory.delete("/point/private/priority") {
                contentType(ContentType.Application.Json)
                parameter("id", taskId)
            }
            response.retrieveBody()
        }

    override suspend fun search(phrase: String): AttachedTasksResponseDto = withContext(Dispatchers.Default) {
        val response = requestFactory.get("/point/private/search") {
            contentType(ContentType.Application.Json)
            parameter("value", phrase)
        }
        response.retrieveBody()
    }

    override suspend fun edit(data: EditTaskRsDto): Unit = withContext(Dispatchers.Default) {
        val response = requestFactory.patch("/point/private/edit") {
            contentType(ContentType.Application.Json)
            setBody(data)
        }
        response.retrieveBody()
    }

    override suspend fun setPosition(body: RequestOrderTaskDto): Unit = withContext(Dispatchers.Default) {
        val response = requestFactory.post("/point/private/order") {
            contentType(ContentType.Application.Json)
            setBody(body)
        }
        response.retrieveBody()
    }

    override suspend fun allProjects(): AllProjectsRsDto = withContext(Dispatchers.Default) {
        val response = requestFactory.get("/point/private/project/all") {
            contentType(ContentType.Application.Json)
        }
        response.retrieveBody()
    }

    override suspend fun createProject(rq: CreateProjectsRqDto): CreateProjectsRsDto =
        withContext(Dispatchers.Default) {
            val response = requestFactory.post("/point/private/project/create") {
                contentType(ContentType.Application.Json)
                setBody(rq)
            }
            response.retrieveBody()
        }

    override suspend fun getProject(projectId: ProjectId): GetProjectRsDto = withContext(Dispatchers.Default) {
        val response = requestFactory.get("/point/private/project/get") {
            contentType(ContentType.Application.Json)
            parameter("id", projectId)
        }
        response.retrieveBody()
    }

    override suspend fun allLabels(): LabelRsDto = withContext(Dispatchers.Default) {
        val response = requestFactory.get("/point/private/label/all") {
            contentType(ContentType.Application.Json)
        }
        response.retrieveBody()
    }

    override suspend fun setLabels(rq: SetLabelRqDto): Unit = withContext(Dispatchers.Default) {
        val response = requestFactory.patch("/point/private/label/set") {
            contentType(ContentType.Application.Json)
            setBody(rq)
        }
        response.retrieveBody()
    }
}