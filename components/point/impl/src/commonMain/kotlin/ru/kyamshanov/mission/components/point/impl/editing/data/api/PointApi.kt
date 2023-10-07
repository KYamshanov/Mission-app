package ru.kyamshanov.mission.components.point.impl.editing.data.api

import ru.kyamshanov.mission.components.point.impl.editing.data.model.GetTaskRsDto
import ru.kyamshanov.mission.components.points.api.common.TaskId

internal interface PointApi {

    suspend fun get(taskId: TaskId): GetTaskRsDto
}