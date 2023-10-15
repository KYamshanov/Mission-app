package ru.kyamshanov.mission.components.points.api.domain

import ru.kyamshanov.mission.components.points.api.common.TaskSlim


interface TaskRepository {

    suspend fun getAll(): Result<List<TaskSlim>>
}