package ru.kyamshanov.mission.components.project.impl.edit.domain.usecase

import ru.kyamshanov.mission.components.project.impl.edit.domain.model.TaskStage

internal interface TaskStagePresentUseCase {

    operator fun invoke(stage: TaskStage): String
}