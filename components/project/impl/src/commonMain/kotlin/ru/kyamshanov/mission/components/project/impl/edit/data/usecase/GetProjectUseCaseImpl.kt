package ru.kyamshanov.mission.components.project.impl.edit.data.usecase

import ru.kyamshanov.mission.components.project.impl.edit.data.api.ProjectApi
import ru.kyamshanov.mission.components.project.impl.edit.data.model.getStage
import ru.kyamshanov.mission.components.project.impl.edit.data.model.toDomain
import ru.kyamshanov.mission.components.project.impl.edit.domain.model.ProjectInfo
import ru.kyamshanov.mission.components.project.impl.edit.domain.usecase.FetchTeamUseCase
import ru.kyamshanov.mission.components.project.impl.edit.domain.usecase.GetProjectUseCase

internal class GetProjectUseCaseImpl(
    private val projectApi: ProjectApi,
    private val fetchTeamUseCase: FetchTeamUseCase,
) : GetProjectUseCase {

    override suspend fun getProjectById(projectId: String): Result<ProjectInfo> = runCatching {
        val projectInfo = projectApi.getProject(projectId = projectId)
        val participants = fetchTeamUseCase.fetchTeam(projectId).getOrDefault(emptyList())

        ProjectInfo(
            id = projectInfo.id,
            title = projectInfo.title,
            description = projectInfo.description,
            participants = participants,
            tasks = projectInfo.tasks.map { it.toDomain() }.sortedBy { it.startAt },
            projectStage = projectInfo.getStage(),
        )
    }
}