package ru.kyamshanov.mission.components.project.impl.edit.data.model

import kotlinx.serialization.Serializable


@Serializable
internal data class RemoveParticipantRqDto(
    val projectId: String,
    val userId: String,
)