package ru.kyamshanov.mission.components.project.impl.edit.domain.model

import kotlinx.datetime.LocalDateTime

internal data class SlimTaskInfo(
    val id: String,
    val title: String,
    val description: String,
    val taskStage: TaskStage,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
    val points: Int?,
    val maxPoints: Int,
)