package ru.kyamshanov.mission.components.project.impl.edit.domain.interactor

import kotlinx.coroutines.flow.StateFlow
import ru.kyamshanov.mission.components.project.impl.edit.domain.model.ProjectEditingScheme
import ru.kyamshanov.mission.components.project.impl.edit.domain.model.ProjectInfo

internal interface ProjectInteractor {

    val editableSchemeStateFlow: StateFlow<ProjectEditingScheme>

    suspend fun fetchProjectById(projectId: String): Result<ProjectInfo>

    fun setTitle(title: String): Result<ProjectInfo>
    fun setDescription(description: String): Result<ProjectInfo>

    suspend fun saveChanges(): Result<Unit>
}