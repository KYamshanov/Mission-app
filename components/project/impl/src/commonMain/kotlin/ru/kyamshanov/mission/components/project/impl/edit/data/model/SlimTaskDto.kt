package ru.kyamshanov.mission.components.project.impl.edit.data.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import ru.kyamshanov.mission.components.project.impl.edit.domain.model.SlimTaskInfo

@Serializable
internal data class SlimTaskDto(
    val id: String,
    val title: String,
    val description: String,
    val taskStage: TaskStageDto,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
    val points: Int?,
    val maxPoints: Int,
)

internal fun SlimTaskDto.toDomain() = SlimTaskInfo(
    id = id,
    title = title,
    description = description,
    taskStage = taskStage.toDomain(
        startAt = startAt,
        endAt = endAt,
        points = points,
    ),
    startAt = startAt,
    endAt = endAt,
    points = points,
    maxPoints = maxPoints
)