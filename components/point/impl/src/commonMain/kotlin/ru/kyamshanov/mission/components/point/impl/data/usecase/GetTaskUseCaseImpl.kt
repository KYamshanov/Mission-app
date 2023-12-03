package ru.kyamshanov.mission.components.point.impl.data.usecase

import ru.kyamshanov.mission.components.point.impl.data.api.PointApi
import ru.kyamshanov.mission.components.point.impl.data.model.GetTaskRsDto
import ru.kyamshanov.mission.components.point.impl.data.model.TaskPriorityDto
import ru.kyamshanov.mission.components.point.impl.data.model.TaskPriorityDto.*
import ru.kyamshanov.mission.components.point.impl.data.model.TaskStatusDto
import ru.kyamshanov.mission.components.point.impl.data.model.TaskStatusDto.*
import ru.kyamshanov.mission.components.point.impl.data.model.toDomain
import ru.kyamshanov.mission.components.point.impl.domain.usecase.GetTaskUseCase
import ru.kyamshanov.mission.components.point.impl.domain.models.TaskModel
import ru.kyamshanov.mission.components.points.api.common.TaskId
import ru.kyamshanov.mission.components.points.api.common.TaskPriority
import ru.kyamshanov.mission.components.points.api.common.TaskStatus

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
    completionTime = completionTime,
    type = type.toDomain(),
    status = status.toDomain(),
    priority = priority?.toDomain(),
    editingRules = this.editingRules.toDomain(),
    labels = labels.map { it.toDomain() }
)

internal fun TaskStatusDto.toDomain(): TaskStatus = when (this) {
    CREATED -> TaskStatus.CREATED
    IN_PROCESSING -> TaskStatus.IN_PROCESSING
    COMPLETED -> TaskStatus.COMPLETED
}

internal fun TaskPriorityDto.toDomain(): TaskPriority = when (this) {
    PRIMARY -> TaskPriority.PRIMARY
    LOW -> TaskPriority.LOW
}
