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
    val updateTime: LocalDateTime? = null,
    val completionTime: LocalDateTime? = null,
    val priority: TaskPriority? = null,
    val status: TaskStatus = TaskStatus.CREATED,
    val type: TaskType?,
)
