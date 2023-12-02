package ru.kyamshanov.mission.components.points.api.domain

import ru.kyamshanov.mission.components.points.api.common.ProjectSlim
import ru.kyamshanov.mission.components.points.api.common.TaskId
import ru.kyamshanov.mission.components.points.api.common.TaskSlim


interface ProjectsRepository {

    suspend fun getAll(): Result<List<ProjectSlim>>
}