package ru.kyamshanov.mission.components.project.impl.edit.domain.usecase

import ru.kyamshanov.mission.components.project.api.common.ProjectId
import ru.kyamshanov.mission.components.project.impl.edit.domain.model.ParticipantInfo

internal interface FetchTeamUseCase {

    suspend fun fetchTeam(projectId: ProjectId): Result<List<ParticipantInfo>>
}