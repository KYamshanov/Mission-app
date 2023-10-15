package ru.kyamshanov.mission.components.point.impl.data.api

import ru.kyamshanov.mission.components.point.impl.data.model.*
import ru.kyamshanov.mission.components.point.impl.data.model.CreateTaskRequestDto
import ru.kyamshanov.mission.components.point.impl.data.model.CreateTaskResponseDto
import ru.kyamshanov.mission.components.points.api.common.TaskId

internal interface PointApi {

    suspend fun get(taskId: TaskId): GetTaskRsDto

    suspend fun allProjects(): AttachedTasksResponseDto

    suspend fun create(rq: CreateTaskRequestDto): CreateTaskResponseDto

    suspend fun delete(taskId: TaskId)

    suspend fun setType(taskId: TaskId, taskTypeDto: TaskTypeDto)

    suspend fun setStatus(taskId: TaskId, taskStatusDto: TaskStatusDto)
}