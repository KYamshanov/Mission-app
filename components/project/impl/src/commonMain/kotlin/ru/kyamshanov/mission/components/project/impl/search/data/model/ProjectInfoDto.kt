package ru.kyamshanov.mission.components.project.impl.search.data.model

import kotlinx.serialization.Serializable

@Serializable
internal data class ProjectInfoDto(
    val id: String,
    val title: String,
    val description: String,
)

