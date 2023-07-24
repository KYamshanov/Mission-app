package ru.kyamshanov.mission.components.project.impl.edit.ui.model

import ru.kyamshanov.mission.components.project.impl.edit.domain.model.SlimTaskInfo
import ru.kyamshanov.mission.components.project.impl.edit.domain.model.TaskPointsInfo

internal fun SlimTaskInfo.toStagePointInfo() = TaskPointsInfo(
    maxPoints = maxPoints,
    currentPoints = points,
    taskId = id,
    taskTitle = title
)