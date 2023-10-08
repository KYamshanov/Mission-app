package ru.kyamshanov.mission.components.point.impl.domain.usecase

import ru.kyamshanov.mission.components.point.impl.domain.models.TaskModel
import ru.kyamshanov.mission.components.points.api.common.TaskId

internal interface GetTaskUseCase {

    suspend fun fetch(taskId: TaskId): Result<TaskModel>
}