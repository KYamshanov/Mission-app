package ru.kyamshanov.mission.components.project.impl.edit.data.model

import kotlinx.serialization.Serializable

@Serializable
internal data class GetTeamRsDto(
    val project: String,
    val participants: Collection<ParticipantDto>,
)