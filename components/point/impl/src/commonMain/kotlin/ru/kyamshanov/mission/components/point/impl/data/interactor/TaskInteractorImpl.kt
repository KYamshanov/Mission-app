package ru.kyamshanov.mission.components.point.impl.data.interactor

import ru.kyamshanov.mission.components.point.impl.data.api.PointApi
import ru.kyamshanov.mission.components.point.impl.data.model.*
import ru.kyamshanov.mission.components.point.impl.data.model.CreateTaskRequestDto
import ru.kyamshanov.mission.components.point.impl.data.model.toDto
import ru.kyamshanov.mission.components.point.impl.domain.interactor.TaskInteractor
import ru.kyamshanov.mission.components.points.api.common.TaskId
import ru.kyamshanov.mission.components.points.api.common.TaskPriority
import ru.kyamshanov.mission.components.points.api.common.TaskPriority.*
import ru.kyamshanov.mission.components.points.api.common.TaskStatus
import ru.kyamshanov.mission.components.points.api.common.TaskType

internal class TaskInteractorImpl(
    private val api: PointApi
) : TaskInteractor {
    override suspend fun create(title: String, description: String): Result<TaskId> = runCatching {
        api.create(CreateTaskRequestDto(title, description)).id
    }

    override suspend fun delete(taskId: TaskId): Result<Unit> = runCatching {
        api.delete(taskId)
    }

    override suspend fun setType(taskId: TaskId, taskType: TaskType?): Result<Unit> = runCatching {
        api.setType(taskId, taskType.toDto())
    }

    override suspend fun setStatus(taskId: TaskId, taskStatus: TaskStatus): Result<Unit> = runCatching {
        api.setStatus(taskId, taskStatus.toDto())
    }

    override suspend fun setPriority(taskId: TaskId, priority: TaskPriority?): Result<Unit> = runCatching {
        if (priority != null) api.setPriority(taskId, priority.toDto())
        else api.removePriority(taskId)
    }
}

private fun TaskPriority.toDto(): TaskPriorityDto = when (this) {
    PRIMARY -> TaskPriorityDto.PRIMARY
    LOW -> TaskPriorityDto.LOW
}

private fun TaskStatus.toDto(): TaskStatusDto = when (this) {
    TaskStatus.CREATED -> TaskStatusDto.CREATED
    TaskStatus.IN_PROCESSING -> TaskStatusDto.IN_PROCESSING
    TaskStatus.COMPLETED -> TaskStatusDto.COMPLETED
}
