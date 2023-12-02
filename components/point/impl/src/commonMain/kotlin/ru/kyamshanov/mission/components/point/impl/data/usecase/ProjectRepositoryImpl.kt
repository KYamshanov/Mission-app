package ru.kyamshanov.mission.components.point.impl.data.usecase

import ru.kyamshanov.mission.components.point.impl.data.api.PointApi
import ru.kyamshanov.mission.components.point.impl.data.model.AllProjectsRsDto
import ru.kyamshanov.mission.components.points.api.common.ProjectSlim
import ru.kyamshanov.mission.components.points.api.domain.ProjectsRepository

internal class ProjectRepositoryImpl(
    private val pointApi: PointApi,
) : ProjectsRepository {

    override suspend fun getAll(): Result<List<ProjectSlim>> = runCatching {
        pointApi.allProjects().toDomain()
    }
}

private fun AllProjectsRsDto.toDomain(): List<ProjectSlim> = items.map {
    ProjectSlim(it.id, it.title)
}
