package ru.kyamshanov.mission.components.point.impl.data.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
internal data class SearchRsDto(
    val tasks: List<TaskSlim>,
    val projects: List<ProjectSlim>
) {

    @Serializable
    data class TaskSlim(
        val id: String,
        val title: String,
        val creationTime: LocalDateTime,
        val completionTime: LocalDateTime?,
        val priority: TaskPriorityDto?,
        val status: TaskStatusDto,
        val type: TaskTypeDto,
    )

    @Serializable
    data class ProjectSlim(
        val id: String,
        val title: String,
    )
}