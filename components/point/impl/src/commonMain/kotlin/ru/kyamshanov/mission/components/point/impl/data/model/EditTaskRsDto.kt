package ru.kyamshanov.mission.components.point.impl.data.model

import kotlinx.serialization.Serializable

@Serializable
internal data class EditTaskRsDto(
    val id: String,
    val title: String? = null,
    val description: String? = null,
)
