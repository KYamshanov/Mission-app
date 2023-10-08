package ru.kyamshanov.mission.components.point.impl.presentation

import ru.kyamshanov.mission.components.points.api.common.TaskId
import ru.kyamshanov.mission.components.points.api.presentation.navigation.TaskLauncher
import ru.kyamshanov.mission.core.navigation.api.Navigator

internal class TaskLauncherImpl(
    private val navigator: Navigator
) : TaskLauncher {
    override fun launchEditing(taskId: TaskId) {
        navigator.navigateTo(EditingTaskScreen(taskId))
    }

    override fun launchCreationTask() {
        TODO("Not yet implemented")
    }
}