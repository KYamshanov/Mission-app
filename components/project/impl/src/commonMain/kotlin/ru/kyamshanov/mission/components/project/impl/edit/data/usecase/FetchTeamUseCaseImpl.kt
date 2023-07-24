package ru.kyamshanov.mission.components.project.impl.edit.data.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.kyamshanov.mission.components.project.api.common.ProjectId
import ru.kyamshanov.mission.components.project.impl.edit.data.api.ProjectApi
import ru.kyamshanov.mission.components.project.impl.edit.data.model.toDomain
import ru.kyamshanov.mission.components.project.impl.edit.domain.model.ParticipantInfo
import ru.kyamshanov.mission.components.project.impl.edit.domain.usecase.FetchTeamUseCase
import ru.kyamshanov.mission.session_front.api.SessionInfo
import ru.kyamshanov.mission.session_front.api.hasRole
import ru.kyamshanov.mission.session_front.api.model.UserRole

internal class FetchTeamUseCaseImpl(
    private val projectApi: ProjectApi,
    private val sessionInfo: SessionInfo,
) : FetchTeamUseCase {

    override suspend fun fetchTeam(projectId: ProjectId): Result<List<ParticipantInfo>> =
        runCatching {
            withContext(Dispatchers.Default) {
                when {
                    sessionInfo.hasRole(UserRole.MANAGER) -> projectApi.getManagedTeam(projectId = projectId)
                    else -> projectApi.getTeam(projectId = projectId)
                }.participants.map { it.toDomain() }
            }
        }
}