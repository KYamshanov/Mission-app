package ru.kyamshanov.mission.components.point.impl.editing.data

import ru.kyamshanov.mission.components.point.impl.editing.data.api.PointApi
import ru.kyamshanov.mission.components.point.impl.editing.data.model.GetTaskRsDto
import ru.kyamshanov.mission.components.point.impl.editing.domain.GetTaskUseCase
import ru.kyamshanov.mission.components.point.impl.editing.domain.models.TaskModel
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
