package ru.kyamshanov.mission.components.point.impl.data.model

import kotlinx.serialization.Serializable

@Serializable
internal data class GetProjectRsDto(
    val id: String,
    val title: String,
    val description: String?,
    val editingRules: EditingProjectRulesDto?,
    val labels: List<LabelDto>
)
