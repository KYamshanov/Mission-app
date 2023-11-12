package ru.kyamshanov.mission.components.points.api.common

data class TaskSlim(
    val id: TaskId,
    val title: String,
    val priority: TaskPriority? = null,
    val status: TaskStatus = TaskStatus.CREATED,
    val type: TaskType?,
    val offset: Int
)
