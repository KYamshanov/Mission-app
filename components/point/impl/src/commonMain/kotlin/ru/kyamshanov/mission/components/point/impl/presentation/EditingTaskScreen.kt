package ru.kyamshanov.mission.components.point.impl.presentation

import ru.kyamshanov.mission.components.points.api.common.TaskId
import ru.kyamshanov.mission.core.navigation.api.Screen

internal expect class EditingTaskScreen actual constructor(taskId: TaskId) : Screen