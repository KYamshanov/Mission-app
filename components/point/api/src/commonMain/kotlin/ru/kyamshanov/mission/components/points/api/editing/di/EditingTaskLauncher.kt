package ru.kyamshanov.mission.components.points.api.editing.di

import ru.kyamshanov.mission.components.points.api.common.TaskId

interface EditingTaskLauncher {

    fun launch(taskId: TaskId)
}