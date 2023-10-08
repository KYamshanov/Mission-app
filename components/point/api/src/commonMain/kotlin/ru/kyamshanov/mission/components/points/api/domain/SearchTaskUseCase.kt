package ru.kyamshanov.mission.components.points.api.domain

import ru.kyamshanov.mission.components.points.api.common.TaskSlim


interface SearchTaskUseCase {

    suspend fun getAll(): Result<List<TaskSlim>>
}