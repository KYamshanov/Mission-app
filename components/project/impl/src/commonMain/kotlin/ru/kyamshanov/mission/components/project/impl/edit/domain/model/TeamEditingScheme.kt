package ru.kyamshanov.mission.components.project.impl.edit.domain.model

internal data class TeamEditingScheme(
    val isMentorEditable: Boolean,
    val isLeaderEditable: Boolean,
    val isTeamEditable: Boolean,
) {

    constructor(isEditable: Boolean) : this(
        isMentorEditable = isEditable,
        isLeaderEditable = isEditable,
        isTeamEditable = isEditable
    )
}