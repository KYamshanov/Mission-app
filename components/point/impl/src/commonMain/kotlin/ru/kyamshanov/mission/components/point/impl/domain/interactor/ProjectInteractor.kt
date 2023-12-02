package ru.kyamshanov.mission.components.point.impl.domain.interactor

import ru.kyamshanov.mission.components.point.impl.data.model.TaskTypeDto
import ru.kyamshanov.mission.components.point.impl.domain.models.ProjectModel
import ru.kyamshanov.mission.components.points.api.common.*

internal interface ProjectInteractor {

    suspend fun create(title: String, description: String): Result<ProjectId>
    suspend fun fetchProject(projectId: ProjectId): Result<ProjectModel>
    suspend fun delete(projectId: ProjectId): Result<Unit>
    suspend fun updateProject(projectId: ProjectId, title: String?, description: String?): Result<Unit>
}