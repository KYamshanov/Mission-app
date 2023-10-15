package ru.kyamshanov.mission.components.point.impl.data.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable


@Serializable
data class AttachedTasksResponseDto(
    val items: List<Task>
) {

    @Serializable
    data class Task(
        val id: String,
        val title: String,
        val creationTime: LocalDateTime,
        val completionTime: LocalDateTime? = null,
        val priority: TaskPriorityDto? = null,
        val status: TaskStatusDto = TaskStatusDto.CREATED,
    )
}