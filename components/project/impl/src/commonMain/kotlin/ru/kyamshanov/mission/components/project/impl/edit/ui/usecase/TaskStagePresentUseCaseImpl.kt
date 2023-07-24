package ru.kyamshanov.mission.components.project.impl.edit.ui.usecase

import ru.kyamshanov.mission.components.project.impl.Res
import ru.kyamshanov.mission.components.project.impl.edit.domain.model.TaskStage
import ru.kyamshanov.mission.components.project.impl.edit.domain.usecase.TaskStagePresentUseCase
import ru.kyamshanov.mission.core.base.api.ResourcesProvider
import ru.kyamshanov.mission.time.api.MissionDateFormatter

internal class TaskStagePresentUseCaseImpl(
    private val resourcesProvider: ResourcesProvider,
    private val dateFormatter: MissionDateFormatter,
) : TaskStagePresentUseCase {

    override fun invoke(stage: TaskStage): String = when (stage) {
        is TaskStage.Finished -> {
            stage.points?.let { points ->
                resourcesProvider.getString(
                    Res.strings.pv_finished_with_points,
                    resourcesProvider.getQuantityString(
                        Res.plurals.pv_point_quantity,
                        points,
                        points
                    )
                )
            } ?: resourcesProvider.getString(Res.strings.pv_finished)
        }

        is TaskStage.InProgress -> resourcesProvider.getString(
            Res.strings.pv_in_progress_with_time,
            dateFormatter(stage.finishAt)
        )

        is TaskStage.Wait -> resourcesProvider.getString(
            Res.strings.pv_wait,
            dateFormatter(stage.startAt)
        )
    }
}