package ru.kyamshanov.mission.components.project.impl.edit.data.interactor

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ru.kyamshanov.mission.components.project.impl.edit.data.api.ProjectApi
import ru.kyamshanov.mission.components.project.impl.edit.data.model.EditProjectRqDto
import ru.kyamshanov.mission.components.project.impl.edit.domain.interactor.ProjectInteractor
import ru.kyamshanov.mission.components.project.impl.edit.domain.model.ProjectEditingScheme
import ru.kyamshanov.mission.components.project.impl.edit.domain.model.ProjectInfo
import ru.kyamshanov.mission.components.project.impl.edit.domain.model.reset
import ru.kyamshanov.mission.components.project.impl.edit.domain.usecase.GetProjectUseCase
import ru.kyamshanov.mission.session_front.api.SessionInfo
import ru.kyamshanov.mission.session_front.api.hasRole
import ru.kyamshanov.mission.session_front.api.model.UserRole

internal class ProjectInteractorImpl(
    private val getProjectUseCase: GetProjectUseCase,
    private val projectApi: ProjectApi,
    sessionInfo: SessionInfo,
) : ProjectInteractor {

    private val _editableSchemeStateFlow = MutableStateFlow(
        if (sessionInfo.hasRole(UserRole.MANAGER)) ProjectEditingScheme(isEditable = true)
        else ProjectEditingScheme(isEditable = false)
    )
    override val editableSchemeStateFlow: StateFlow<ProjectEditingScheme> =
        _editableSchemeStateFlow.asStateFlow()

    private var currentProject: ProjectInfo? = null

    override suspend fun fetchProjectById(projectId: String): Result<ProjectInfo> =
        getProjectUseCase.getProjectById(projectId)
            .onSuccess { currentProject = it }

    override fun setTitle(title: String): Result<ProjectInfo> = runCatching {
        if (editableSchemeStateFlow.value.isEditableTitle.not()) throw AssertionError("Title editing is not available")
        requireNotNull(currentProject) { "Project was not loaded" }
            .copy(title = title)
            .also { project ->
                currentProject = project
                _editableSchemeStateFlow.update { it.copy(titleEdited = true) }
            }
    }

    override fun setDescription(description: String): Result<ProjectInfo> = runCatching {
        if (editableSchemeStateFlow.value.isEditableDescription.not()) throw AssertionError("Description editing is not available")
        requireNotNull(currentProject) { "Project was not loaded" }
            .copy(description = description)
            .also { project ->
                currentProject = project
                _editableSchemeStateFlow.update { it.copy(descriptionEdited = true) }
            }
    }

    override suspend fun saveChanges(): Result<Unit> = runCatching {
        val task = requireNotNull(currentProject) { "Project was not fetched" }
        val editableScheme = _editableSchemeStateFlow.value
        val edited = editableScheme.hasChanges
        val request = EditProjectRqDto(
            projectId = task.id,
            title = task.title.takeIf { editableScheme.titleEdited },
            description = task.description.takeIf { editableScheme.descriptionEdited },
        )
        if (edited.not()) throw AssertionError("Project information has no changes")
        projectApi.editProject(request)
        editableScheme.reset().also { _editableSchemeStateFlow.value = it }
    }
}