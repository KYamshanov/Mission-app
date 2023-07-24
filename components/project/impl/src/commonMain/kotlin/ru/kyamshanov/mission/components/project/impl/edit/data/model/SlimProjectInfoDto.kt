package ru.kyamshanov.mission.components.project.impl.edit.data.model

import kotlinx.serialization.Serializable


@Serializable
internal data class SlimProjectInfoDto(
    val id: String,
    val title: String,
    val description: String,
)

