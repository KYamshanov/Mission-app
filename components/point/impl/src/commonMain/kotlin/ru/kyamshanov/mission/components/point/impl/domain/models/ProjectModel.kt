package ru.kyamshanov.mission.components.point.impl.domain.models

internal data class ProjectModel(
    val id: String,
    val title: String,
    val description: String?,
    val editingRules: ProjectEditingRules,
    val labels: List<LabelModel>
)
