package ru.kyamshanov.mission.components.point.impl.domain.interactor

import ru.kyamshanov.mission.components.point.impl.data.model.TaskTypeDto
import ru.kyamshanov.mission.components.points.api.common.TaskId
import ru.kyamshanov.mission.components.points.api.common.TaskStatus
import ru.kyamshanov.mission.components.points.api.common.TaskType

internal interface TaskInteractor {

    suspend fun create(title: String, description: String): Result<Unit>

    suspend fun delete(taskId: TaskId): Result<Unit>

    suspend fun setType(taskId: TaskId, taskType: TaskType): Result<Unit>

    suspend fun setStatus(taskId: TaskId, taskStatus: TaskStatus): Result<Unit>
}