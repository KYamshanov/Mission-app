package ru.kyamshanov.mission.components.project.impl.edit.data.model

import kotlinx.serialization.Serializable

@Serializable
internal data class EditProjectRqDto(
    val projectId: String,
    val title: String?,
    val description: String?,
)


