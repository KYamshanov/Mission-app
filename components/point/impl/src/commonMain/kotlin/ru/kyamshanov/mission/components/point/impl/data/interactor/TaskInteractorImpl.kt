package ru.kyamshanov.mission.components.point.impl.data.interactor

import ru.kyamshanov.mission.components.point.impl.data.api.PointApi
import ru.kyamshanov.mission.components.point.impl.data.model.CreateTaskRequestDto
import ru.kyamshanov.mission.components.point.impl.data.model.TaskType
import ru.kyamshanov.mission.components.point.impl.domain.interactor.TaskInteractor
import ru.kyamshanov.mission.components.points.api.common.TaskId

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
        api.setType(taskId, taskType)
    }
}