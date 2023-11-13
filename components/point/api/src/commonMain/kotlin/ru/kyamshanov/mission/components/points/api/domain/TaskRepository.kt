package ru.kyamshanov.mission.components.points.api.domain

import ru.kyamshanov.mission.components.points.api.common.TaskId
import ru.kyamshanov.mission.components.points.api.common.TaskSlim


interface TaskRepository {

    suspend fun getAll(): Result<List<TaskSlim>>

    suspend fun search(phrase: String): Result<List<TaskSlim>>

    suspend fun setPosition(
        taskId: String,
        putBefore: String?,
        newTaskBefore: String?,
        oldBeforeTask: String?,
        oldAfterTask: String?,
    ): Result<Unit>
}