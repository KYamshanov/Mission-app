package ru.kyamshanov.mission.components.points.api.presentation.navigation

import ru.kyamshanov.mission.components.points.api.common.TaskId

interface TaskLauncher {

    fun launchEditing(taskId: TaskId)

    fun launchCreationTask()
}