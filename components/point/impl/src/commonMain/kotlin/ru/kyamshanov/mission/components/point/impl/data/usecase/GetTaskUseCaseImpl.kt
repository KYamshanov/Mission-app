package ru.kyamshanov.mission.components.point.impl.data.usecase

import ru.kyamshanov.mission.components.point.impl.data.api.PointApi
import ru.kyamshanov.mission.components.point.impl.data.model.GetTaskRsDto
import ru.kyamshanov.mission.components.point.impl.domain.usecase.GetTaskUseCase
import ru.kyamshanov.mission.components.point.impl.domain.models.TaskModel
import ru.kyamshanov.mission.components.points.api.common.TaskId

internal class GetTaskUseCaseImpl(
    private val pointApi: PointApi
) : GetTaskUseCase {
    override suspend fun fetch(taskId: TaskId): Result<TaskModel> = runCatching {
        pointApi.get(taskId).toDomain()
    }
}

private fun GetTaskRsDto.toDomain(): TaskModel = TaskModel(
    id = id,
    title = title,
    description = description,
    creationTime = creationTime,
    updateTime = updateTime,
    completionTime = completionTime
)