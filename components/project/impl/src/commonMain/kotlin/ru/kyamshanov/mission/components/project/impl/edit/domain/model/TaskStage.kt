package ru.kyamshanov.mission.components.project.impl.edit.domain.model

import kotlinx.datetime.LocalDateTime

internal sealed interface TaskStage {

    data class Wait(
        val startAt: LocalDateTime,
    ) : TaskStage

    data class InProgress(
        val finishAt: LocalDateTime,
    ) : TaskStage

    data class Finished(
        val points: Int? = null,
    ) : TaskStage
}