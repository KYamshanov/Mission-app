package ru.kyamshanov.mission.components.point.impl.data.api

import ru.kyamshanov.mission.components.point.impl.data.model.AttachedTasksResponseDto
import ru.kyamshanov.mission.components.point.impl.data.model.GetTaskRsDto
import ru.kyamshanov.mission.components.points.api.common.TaskId

internal interface PointApi {

    suspend fun get(taskId: TaskId): GetTaskRsDto

    suspend fun allProjects(): AttachedTasksResponseDto
}