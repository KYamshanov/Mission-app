package ru.kyamshanov.mission.components.point.impl.data.model

import kotlinx.serialization.Serializable

@Serializable
internal data class CreateProjectsRqDto(
    val title: String,
    val description: String?,
)

@Serializable
internal data class CreateProjectsRsDto(
    val id: String
)
