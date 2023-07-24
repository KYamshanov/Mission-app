package ru.kyamshanov.mission.components.project.impl.edit.domain.usecase

import ru.kyamshanov.mission.components.project.impl.edit.domain.model.ProjectInfo

internal interface GetProjectUseCase {

    suspend fun getProjectById(projectId: String): Result<ProjectInfo>
}