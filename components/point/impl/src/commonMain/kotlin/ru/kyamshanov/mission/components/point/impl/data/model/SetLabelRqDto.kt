package ru.kyamshanov.mission.components.point.impl.data.model

import kotlinx.serialization.Serializable

@Serializable
internal data class SetLabelRqDto(
    val taskId: String,
    val labels: List<String>
)