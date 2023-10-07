package ru.kyamshanov.mission.components.point.impl.editing.data.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class GetTaskRsDto(
    val id: String? = null,
    val title: String,
    val description: String,
    val creationTime: LocalDateTime,
    val updateTime: LocalDateTime? = null,
    val completionTime: LocalDateTime? = null,
    val priority: TaskPriorityDto? = null,
    val status: TaskStatusDto = TaskStatusDto.CREATED,
)
