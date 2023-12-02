package ru.kyamshanov.mission.components.point.impl.domain.models

internal data class ProjectEditingRules(
    val isTitleEditable: Boolean,
    val isDescriptionEditable: Boolean
) {

    constructor() : this(false)

    constructor(isEditable: Boolean) : this(isEditable, isEditable)
}
