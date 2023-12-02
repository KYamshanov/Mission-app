package ru.kyamshanov.mission.components.point.impl.domain.models

import kotlinx.datetime.LocalDateTime
import ru.kyamshanov.mission.components.points.api.common.TaskPriority
import ru.kyamshanov.mission.components.points.api.common.TaskStatus
import ru.kyamshanov.mission.components.points.api.common.TaskType

internal data class TaskModel(
    val id: String? = null,
    val title: String,
    val description: String,
    val creationTime: LocalDateTime,
    val updateTime: LocalDateTime?,
    val completionTime: LocalDateTime?,
    val priority: TaskPriority?,
    val status: TaskStatus,
    val type: TaskType?,
    val editingRules: TaskEditingRules,
    val labels: List<LabelModel>,
)
