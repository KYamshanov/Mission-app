package ru.kyamshanov.mission.components.project.impl.edit.domain.interactor

import kotlinx.coroutines.flow.StateFlow
import ru.kyamshanov.mission.components.project.impl.edit.domain.model.ParticipantInfo
import ru.kyamshanov.mission.components.project.impl.edit.domain.model.TeamEditingScheme
import ru.kyamshanov.mission.components.project.impl.edit.domain.model.TeamInfo

internal interface TeamInteractor {

    val participantsStateFlow: StateFlow<TeamInfo>

    val teamEditingSchemeStateFlow: StateFlow<TeamEditingScheme>

    suspend fun setLeader(participant: ParticipantInfo): Result<Unit>
    suspend fun setMentor(participant: ParticipantInfo): Result<Unit>

    suspend fun removeParticipant(participant: ParticipantInfo): Result<Unit>

    suspend fun addParticipant(participant: ParticipantInfo): Result<Unit>
}