package ru.kyamshanov.mission.components.project.impl.edit.data.model

import kotlinx.datetime.LocalDateTime
import ru.kyamshanov.mission.components.project.impl.edit.domain.model.TaskStage

internal enum class TaskStageDto {
    WAIT,
    IN_PROGRESS,
    FINISHED
}

internal fun TaskStageDto.toDomain(
    startAt: LocalDateTime,
    endAt: LocalDateTime,
    points: Int?
): TaskStage = when (this) {
    TaskStageDto.WAIT -> TaskStage.Wait(startAt)
    TaskStageDto.IN_PROGRESS -> TaskStage.InProgress(endAt)
    TaskStageDto.FINISHED -> TaskStage.Finished(points)
}