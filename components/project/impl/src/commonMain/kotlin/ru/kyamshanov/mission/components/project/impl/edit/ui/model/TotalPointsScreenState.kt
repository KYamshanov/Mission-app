package ru.kyamshanov.mission.project_view.impl.ui.model

import ru.kyamshanov.mission.components.project.impl.edit.domain.model.TaskPointsEditingScheme
import ru.kyamshanov.mission.components.project.impl.edit.ui.model.TotalPointsInfo

internal data class TotalPointsScreenState(
    val totalPointsInfo: TotalPointsInfo,
    val editingScheme: TaskPointsEditingScheme,
)