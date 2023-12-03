package ru.kyamshanov.mission.components.point.impl.domain.models

import ru.kyamshanov.mission.components.points.api.common.LabelModel

internal data class ProjectModel(
    val id: String,
    val title: String,
    val description: String?,
    val editingRules: ProjectEditingRules,
    val labels: List<LabelModel>
)
