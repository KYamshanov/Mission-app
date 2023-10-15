package ru.kyamshanov.mission.components.point.impl.data.interactor

import ru.kyamshanov.mission.components.point.impl.data.api.PointApi
import ru.kyamshanov.mission.components.point.impl.data.model.CreateTaskRequestDto
import ru.kyamshanov.mission.components.point.impl.data.model.TaskStatusDto
import ru.kyamshanov.mission.components.point.impl.data.model.TaskTypeDto
import ru.kyamshanov.mission.components.point.impl.domain.interactor.TaskInteractor
import ru.kyamshanov.mission.components.points.api.common.TaskId
import ru.kyamshanov.mission.components.points.api.common.TaskStatus
import ru.kyamshanov.mission.components.points.api.common.TaskType

internal class TaskInteractorImpl(
    private val api: PointApi
) : TaskInteractor {
    override suspend fun create(title: String, description: String): Result<Unit> = runCatching {
        api.create(CreateTaskRequestDto(title, description))
    }

    override suspend fun delete(taskId: TaskId): Result<Unit> = runCatching {
        api.delete(taskId)
    }

    override suspend fun setType(taskId: TaskId, taskType: TaskType): Result<Unit> = runCatching {
        api.setType(taskId, taskType.toDto())
    }

    override suspend fun setStatus(taskId: TaskId, taskStatus: TaskStatus): Result<Unit> = runCatching {
        api.setStatus(taskId, taskStatus.toDto())
    }
}

private fun TaskType.toDto(): TaskTypeDto = when (this) {
    TaskType.TODAYS -> TaskTypeDto.TODAYS
    TaskType.WEEKS -> TaskTypeDto.WEEKS
}

private fun TaskStatus.toDto(): TaskStatusDto = when (this) {
    TaskStatus.CREATED -> TaskStatusDto.CREATED
    TaskStatus.IN_PROCESSING -> TaskStatusDto.IN_PROCESSING
    TaskStatus.COMPLETED -> TaskStatusDto.COMPLETED
}
