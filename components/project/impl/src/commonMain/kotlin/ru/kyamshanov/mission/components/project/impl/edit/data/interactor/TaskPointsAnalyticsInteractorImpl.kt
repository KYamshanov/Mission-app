package ru.kyamshanov.mission.components.project.impl.edit.data.interactor

import ru.kyamshanov.mission.components.project.api.common.TaskId
import ru.kyamshanov.mission.components.project.impl.edit.data.api.ProjectApi
import ru.kyamshanov.mission.components.project.impl.edit.data.model.EditTaskRqDto
import ru.kyamshanov.mission.components.project.impl.edit.data.model.EditTaskSetRqDto
import ru.kyamshanov.mission.components.project.impl.edit.domain.interactor.TaskPointsAnalyticsInteractor
import ru.kyamshanov.mission.components.project.impl.edit.domain.model.TaskPointsEditingScheme
import ru.kyamshanov.mission.components.project.impl.edit.domain.model.TaskPointsInfo
import ru.kyamshanov.mission.components.project.impl.edit.domain.model.reset
import ru.kyamshanov.mission.project_view.impl.utils.findIndexed
import ru.kyamshanov.mission.session_front.api.SessionInfo
import ru.kyamshanov.mission.session_front.api.hasRole
import ru.kyamshanov.mission.session_front.api.model.UserRole

internal class TaskPointsAnalyticsInteractorImpl(
    sessionInfo: SessionInfo,
    private val projectApi: ProjectApi,
) : TaskPointsAnalyticsInteractor {

    private var currentTasks: List<TaskPointsInfo> = listOf()
    override var editableScheme: TaskPointsEditingScheme =
        if (sessionInfo.hasRole(UserRole.MANAGER)) TaskPointsEditingScheme(isEditable = true)
        else TaskPointsEditingScheme(isEditable = false)
        private set

    override fun loadTasks(tasks: List<TaskPointsInfo>) {
        currentTasks = tasks.toMutableList()
    }

    override fun setPoints(taskId: TaskId, points: Int?): Result<List<TaskPointsInfo>> =
        runCatching {
            if (!editableScheme.isEditablePoints) throw AssertionError("Editing of Points is not available")
            val mutableTasks = currentTasks.toMutableList()
            mutableTasks
                .findIndexed { it.taskId == taskId }
                ?.let { (index, info) ->
                    mutableTasks[index] = info.copy(currentPoints = points)
                    editableScheme.addChangesTask(taskId)
                    currentTasks = mutableTasks
                } ?: throw IllegalStateException("Task with id $taskId was not found")
            currentTasks
        }

    override suspend fun saveChanges(): Result<TaskPointsEditingScheme> = runCatching {
        if (currentTasks.isEmpty()) throw AssertionError("Tasks are empty")
        val edited = editableScheme.hasChanges
        val request: EditTaskSetRqDto = editableScheme.pointsEdited.map { taskId ->
            EditTaskRqDto(
                taskId = taskId,
                points = requireNotNull(currentTasks.find { it.taskId == taskId }) { "Task not found" }.currentPoints
                    ?: -1
            )
        }
        if (!edited) throw AssertionError("Project information has no changes")
        projectApi.editTasks(request)
        editableScheme.reset().also { editableScheme = it }
    }
}