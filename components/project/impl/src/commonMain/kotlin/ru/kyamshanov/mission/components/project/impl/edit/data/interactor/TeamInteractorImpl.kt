package ru.kyamshanov.mission.components.project.impl.edit.data.interactor

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.kyamshanov.mission.components.project.impl.edit.data.api.ProjectApi
import ru.kyamshanov.mission.components.project.impl.edit.data.model.AddParticipantRqDto
import ru.kyamshanov.mission.components.project.impl.edit.data.model.ParticipantDto
import ru.kyamshanov.mission.components.project.impl.edit.data.model.RemoveParticipantRqDto
import ru.kyamshanov.mission.components.project.impl.edit.data.model.SetRoleRqDto
import ru.kyamshanov.mission.components.project.impl.edit.data.model.toDto
import ru.kyamshanov.mission.components.project.impl.edit.domain.interactor.TeamInteractor
import ru.kyamshanov.mission.components.project.impl.edit.domain.model.ParticipantInfo
import ru.kyamshanov.mission.components.project.impl.edit.domain.model.TeamEditingScheme
import ru.kyamshanov.mission.components.project.impl.edit.domain.model.TeamInfo
import ru.kyamshanov.mission.components.project.impl.edit.domain.usecase.FetchTeamUseCase
import ru.kyamshanov.mission.components.project.impl.edit.domain.usecase.FilterParticipantsUseCase
import ru.kyamshanov.mission.session_front.api.SessionInfo
import ru.kyamshanov.mission.session_front.api.hasRole
import ru.kyamshanov.mission.session_front.api.model.UserRole

internal class TeamInteractorImpl(
    private val projectId: String,
    coroutineScope: CoroutineScope,
    private val projectApi: ProjectApi,
    private val fetchTeamUseCase: FetchTeamUseCase,
    private val filterParticipantsUseCase: FilterParticipantsUseCase,
    sessionInfo: SessionInfo,
) : TeamInteractor {

    private val _participantsStateFlow = MutableStateFlow(TeamInfo())
    override val participantsStateFlow = _participantsStateFlow.asStateFlow()

    private val _teamEditingSchemeStateFlow = MutableStateFlow(
        if (sessionInfo.hasRole(UserRole.MANAGER)) TeamEditingScheme(isEditable = true)
        else TeamEditingScheme(isEditable = false)
    )
    override val teamEditingSchemeStateFlow: StateFlow<TeamEditingScheme> =
        _teamEditingSchemeStateFlow.asStateFlow()

    init {
        coroutineScope.launch(Dispatchers.Default) {
            fetchTeamUseCase.fetchTeam(projectId)
                .onSuccess { publishParticipants(it) }
        }
    }

    override suspend fun setLeader(participant: ParticipantInfo): Result<Unit> = runCatching {
        projectApi.setRole(
            SetRoleRqDto(
                projectId = projectId,
                userId = participant.userId,
                role = ParticipantDto.Role.LEADER
            )
        )
        publishParticipants(fetchTeamUseCase.fetchTeam(projectId).getOrThrow())
    }

    override suspend fun setMentor(participant: ParticipantInfo): Result<Unit> = runCatching {
        projectApi.setRole(
            SetRoleRqDto(
                projectId = projectId,
                userId = participant.userId,
                role = ParticipantDto.Role.MENTOR
            )
        )
        publishParticipants(fetchTeamUseCase.fetchTeam(projectId).getOrThrow())
    }

    override suspend fun removeParticipant(participant: ParticipantInfo): Result<Unit> =
        runCatching {
            projectApi.removeParticipant(
                RemoveParticipantRqDto(
                    projectId = projectId,
                    userId = participant.userId,
                )
            )
            publishParticipants(fetchTeamUseCase.fetchTeam(projectId).getOrThrow())
        }

    override suspend fun addParticipant(participant: ParticipantInfo): Result<Unit> = runCatching {
        projectApi.addParticipant(
            AddParticipantRqDto(
                projectId = projectId,
                userId = participant.userId,
                role = participant.role.toDto()
            )
        )
        publishParticipants(fetchTeamUseCase.fetchTeam(projectId).getOrThrow())
    }

    private suspend fun publishParticipants(participants: List<ParticipantInfo>) {
        _participantsStateFlow.emit(filterParticipantsUseCase.invoke(participants))
    }
}