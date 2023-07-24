package ru.kyamshanov.mission.project_view.impl.ui.model

import ru.kyamshanov.mission.components.project.impl.edit.domain.model.ProjectEditingScheme
import ru.kyamshanov.mission.components.project.impl.edit.domain.model.ProjectInfo
import ru.kyamshanov.mission.components.project.impl.edit.ui.model.TotalPointsInfo

internal data class ProjectScreenState(
    val loading: Boolean,
    val projectInfo: ProjectInfo?,
    val editingScheme: ProjectEditingScheme,
    val totalPointsInfo: TotalPointsInfo,
) {

    val participantsCount = projectInfo?.participants?.size ?: 0

    constructor() : this(true, null, ProjectEditingScheme(isEditable = false), TotalPointsInfo())
}
