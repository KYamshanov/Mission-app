package ru.kyamshanov.mission.project_view.impl.ui.model

import ru.kyamshanov.mission.components.project.impl.edit.domain.model.TeamEditingScheme
import ru.kyamshanov.mission.components.project.impl.edit.domain.model.TeamInfo

internal data class ParticipantsListScreenState(
    val teamInfo: TeamInfo,
    val teamEditingScheme: TeamEditingScheme,
) {

    constructor() : this(TeamInfo(), TeamEditingScheme(isEditable = false))
}