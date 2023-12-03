package ru.kyamshanov.mission.components.point.impl.data.model

import kotlinx.serialization.Serializable
import ru.kyamshanov.mission.components.points.api.common.LabelModel

@Serializable
internal data class LabelRsDto(
    val items: List<LabelDto>
)

@Serializable
internal data class LabelDto(
    val id: String,
    val title: String,
    val color: Long,
)

internal fun LabelDto.toDomain() = LabelModel(id = id, title = title, color = color)

