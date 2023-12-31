package ru.kyamshanov.mission.components.point.impl.data.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable


@Serializable
internal data class AttachedTasksResponseDto(
    val items: List<Task>
) {

    @Serializable
    internal data class Task(
        val id: String,
        val title: String,
        val creationTime: LocalDateTime,
        val completionTime: LocalDateTime? = null,
        val priority: TaskPriorityDto? = null,
        val status: TaskStatusDto = TaskStatusDto.CREATED,
        val type: TaskTypeDto,
    )
}