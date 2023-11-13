package ru.kyamshanov.mission.components.point.impl.data.model

import kotlinx.serialization.Serializable

@Serializable
internal data class RequestOrderTaskDto(
    val taskId: String,
    val oldPlaceBefore: String?,
    val newPlaceBefore: String?
)