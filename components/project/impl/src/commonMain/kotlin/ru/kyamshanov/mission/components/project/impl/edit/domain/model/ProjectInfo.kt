package ru.kyamshanov.mission.components.project.impl.edit.domain.model

internal data class ProjectInfo(
    val id: String,
    val title: String,
    val description: String,
    val participants: List<ParticipantInfo>,
    val tasks: List<SlimTaskInfo>,
    val projectStage: ProjectStage,
)
