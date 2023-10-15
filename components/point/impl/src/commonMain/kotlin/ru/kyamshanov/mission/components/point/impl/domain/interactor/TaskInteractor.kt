package ru.kyamshanov.mission.components.point.impl.domain.interactor

import ru.kyamshanov.mission.components.points.api.common.TaskId

internal interface TaskInteractor {

    suspend fun create(title: String, description: String): Result<Unit>

    suspend fun delete(taskId: TaskId): Result<Unit>
}