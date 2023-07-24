package ru.kyamshanov.mission.components.project.impl.edit.data.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import ru.kyamshanov.mission.components.project.impl.edit.domain.model.TaskInfo

@Serializable
internal data class GetTaskRsDto(
    val id: String,
    val title: String,
    val text: String,
    val createdAt: LocalDateTime,
    val taskStage: TaskStageDto,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
    val maxPoints: Int,
    val points: Int,
)

internal fun GetTaskRsDto.toDomain() = TaskInfo(
    id = id,
    title = title,
    description = text,
    taskStage = taskStage.toDomain(startAt, endAt, points),
    startAt = startAt,
    endAt = endAt,
    points = points,
    createdAt = createdAt,
    maxPoints = maxPoints
)