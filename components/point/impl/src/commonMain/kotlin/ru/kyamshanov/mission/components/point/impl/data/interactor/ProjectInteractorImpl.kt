package ru.kyamshanov.mission.components.point.impl.data.interactor

import ru.kyamshanov.mission.components.point.impl.data.api.PointApi
import ru.kyamshanov.mission.components.point.impl.data.model.*
import ru.kyamshanov.mission.components.point.impl.data.model.CreateProjectsRqDto
import ru.kyamshanov.mission.components.point.impl.data.model.GetProjectRsDto
import ru.kyamshanov.mission.components.point.impl.domain.interactor.ProjectInteractor
import ru.kyamshanov.mission.components.point.impl.domain.interactor.TaskInteractor
import ru.kyamshanov.mission.components.point.impl.domain.models.LabelModel
import ru.kyamshanov.mission.components.point.impl.domain.models.ProjectEditingRules
import ru.kyamshanov.mission.components.point.impl.domain.models.ProjectModel
import ru.kyamshanov.mission.components.points.api.common.*

internal class ProjectInteractorImpl(
    private val api: PointApi
) : ProjectInteractor {
    override suspend fun create(title: String, description: String): Result<ProjectId> = runCatching {
        api.createProject(CreateProjectsRqDto(title, description)).id
    }

    override suspend fun fetchProject(projectId: ProjectId): Result<ProjectModel> = runCatching {
        api.getProject(projectId).toDomain()
    }

    override suspend fun delete(projectId: ProjectId): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun updateProject(projectId: ProjectId, title: String?, description: String?): Result<Unit> {
        TODO("Not yet implemented")
    }
}

private fun GetProjectRsDto.toDomain() =
    ProjectModel(
        id,
        title,
        description,
        ProjectEditingRules(editingRules?.isEditable ?: false),
        labels.map { it.toDomain() })