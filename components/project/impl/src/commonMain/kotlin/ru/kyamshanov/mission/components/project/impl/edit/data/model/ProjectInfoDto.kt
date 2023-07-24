package ru.kyamshanov.mission.components.project.impl.edit.data.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
internal data class ProjectInfoDto(
    val id: String,
    val title: String,
    val description: String,
    val currentTask: GetTaskRsDto?,
    val startAt: LocalDateTime?,
    val endAt: LocalDateTime?,
    val tasks: List<SlimTaskDto>,
    val stage: ProjectStageDto,
)