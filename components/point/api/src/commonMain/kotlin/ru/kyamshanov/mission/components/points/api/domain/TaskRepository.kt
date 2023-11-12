package ru.kyamshanov.mission.components.points.api.domain

import ru.kyamshanov.mission.components.points.api.common.TaskId
import ru.kyamshanov.mission.components.points.api.common.TaskSlim


interface TaskRepository {

    suspend fun getAll(): Result<List<TaskSlim>>

    suspend fun search(phrase: String): Result<List<TaskSlim>>

    suspend fun setPosition(taskId: TaskId, placeBefore: TaskId): Result<Unit>

    suspend fun tailPosition(taskId: TaskId): Result<Unit>

}