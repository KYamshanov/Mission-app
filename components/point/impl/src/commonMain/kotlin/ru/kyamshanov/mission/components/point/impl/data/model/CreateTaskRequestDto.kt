package ru.kyamshanov.mission.components.point.impl.data.model

import kotlinx.serialization.Serializable

@Serializable
internal data class CreateTaskRequestDto(
    val title: String,
    val description: String,
    val priority: TaskPriorityDto? = null,
)