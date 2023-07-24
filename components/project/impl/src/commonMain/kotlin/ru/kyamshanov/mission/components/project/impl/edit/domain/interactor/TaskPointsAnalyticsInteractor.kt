package ru.kyamshanov.mission.components.project.impl.edit.domain.interactor

import ru.kyamshanov.mission.components.project.api.common.TaskId
import ru.kyamshanov.mission.components.project.impl.edit.domain.model.TaskPointsInfo
import ru.kyamshanov.mission.components.project.impl.edit.domain.model.TaskPointsEditingScheme

internal interface TaskPointsAnalyticsInteractor {

    val editableScheme: TaskPointsEditingScheme

    fun loadTasks(tasks: List<TaskPointsInfo>)

    fun setPoints(taskId: TaskId, points: Int?): Result<List<TaskPointsInfo>>

    suspend fun saveChanges(): Result<TaskPointsEditingScheme>
}