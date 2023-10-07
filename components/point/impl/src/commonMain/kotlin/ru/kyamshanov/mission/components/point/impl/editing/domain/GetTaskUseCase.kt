package ru.kyamshanov.mission.components.point.impl.editing.domain

import ru.kyamshanov.mission.components.point.impl.editing.domain.models.TaskModel
import ru.kyamshanov.mission.components.points.api.common.TaskId

internal interface GetTaskUseCase {

    suspend fun fetch(taskId: TaskId): Result<TaskModel>
}