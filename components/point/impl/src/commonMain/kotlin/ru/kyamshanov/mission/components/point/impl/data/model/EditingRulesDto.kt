package ru.kyamshanov.mission.components.point.impl.data.model

import kotlinx.serialization.Serializable
import ru.kyamshanov.mission.components.point.impl.domain.models.TaskEditingRules

@Serializable
internal data class EditingRulesDto(
    val isEditable: Boolean
)

internal fun EditingRulesDto?.toDomain() = TaskEditingRules(this?.isEditable ?: false)