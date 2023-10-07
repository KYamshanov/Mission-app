package ru.kyamshanov.mission.components.point.impl.editing.presentation

import ru.kyamshanov.mission.components.points.api.common.TaskId
import ru.kyamshanov.mission.components.points.api.editing.di.EditingTaskLauncher
import ru.kyamshanov.mission.core.navigation.api.Navigator

internal class EditingTaskLauncherImpl(
    private val navigator: Navigator
) : EditingTaskLauncher {
    override fun launch(taskId: TaskId) {
        navigator.navigateTo(EditingTaskScreen(taskId))
    }
}