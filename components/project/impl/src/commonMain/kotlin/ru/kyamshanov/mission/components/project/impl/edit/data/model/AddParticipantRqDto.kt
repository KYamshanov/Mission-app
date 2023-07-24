package ru.kyamshanov.mission.components.project.impl.edit.data.model

import kotlinx.serialization.Serializable

@Serializable
internal data class AddParticipantRqDto(
    val projectId: String,
    val userId: String,
    val role : ParticipantDto.Role
)