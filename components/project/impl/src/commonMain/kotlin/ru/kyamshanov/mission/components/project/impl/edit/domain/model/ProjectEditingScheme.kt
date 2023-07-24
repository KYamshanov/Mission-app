package ru.kyamshanov.mission.components.project.impl.edit.domain.model

internal data class ProjectEditingScheme(
    val isEditableTitle: Boolean,
    val isEditableDescription: Boolean,
    val isEditableStages: Boolean,
    val titleEdited: Boolean,
    val descriptionEdited: Boolean,
) {

    constructor(isEditable: Boolean) : this(
        isEditableTitle = isEditable,
        isEditableDescription = isEditable,
        isEditableStages = isEditable,
        titleEdited = false,
        descriptionEdited = false,
    )

    val hasChanges: Boolean
        get() = titleEdited or descriptionEdited
}

internal fun ProjectEditingScheme.reset(): ProjectEditingScheme = this.copy(
    titleEdited = false,
    descriptionEdited = false
)