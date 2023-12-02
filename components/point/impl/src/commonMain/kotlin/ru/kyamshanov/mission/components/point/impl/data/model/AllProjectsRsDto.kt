package ru.kyamshanov.mission.components.point.impl.data.model

import kotlinx.serialization.Serializable

@Serializable
data class AllProjectsRsDto(
    val items: List<ProjectSlimDto>
) {
    @Serializable
    data class ProjectSlimDto(
        val id: String,
        val title: String,
    )
}
