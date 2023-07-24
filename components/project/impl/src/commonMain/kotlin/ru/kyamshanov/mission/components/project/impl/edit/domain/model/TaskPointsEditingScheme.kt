package ru.kyamshanov.mission.components.project.impl.edit.domain.model

import ru.kyamshanov.mission.components.project.api.common.TaskId

internal data class TaskPointsEditingScheme(
    val isEditablePoints: Boolean,
    private var _pointsEdited: MutableSet<TaskId>,
) {

    val pointsEdited: MutableSet<TaskId>
        get() = _pointsEdited

    constructor() : this(false)
    constructor(isEditable: Boolean) : this(isEditable, mutableSetOf())

    val hasChanges: Boolean
        get() = pointsEdited.isNotEmpty()

    fun addChangesTask(taskId: TaskId) {
        _pointsEdited.add(taskId)
    }
}

internal fun TaskPointsEditingScheme.reset(): TaskPointsEditingScheme = copy(
    _pointsEdited = mutableSetOf()
)