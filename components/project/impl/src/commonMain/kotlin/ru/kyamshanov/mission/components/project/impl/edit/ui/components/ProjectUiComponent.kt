package ru.kyamshanov.mission.components.project.impl.edit.ui.components

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import ru.kyamshanov.mission.components.project.api.common.ProjectId
import ru.kyamshanov.mission.components.project.api.editing.di.EditProjectComponent
import ru.kyamshanov.mission.components.project.impl.edit.di.EditProjectModuleComponent
import ru.kyamshanov.mission.components.project.impl.edit.ui.model.TotalPointsInfo
import ru.kyamshanov.mission.components.project.impl.edit.ui.model.toSlim
import ru.kyamshanov.mission.components.project.impl.edit.ui.model.toStagePointInfo
import ru.kyamshanov.mission.components.project.impl.edit.ui.screen.ParticipantsListScreen
import ru.kyamshanov.mission.components.project.impl.edit.ui.screen.TotalPointsViewScreen
import ru.kyamshanov.mission.core.navigation.api.di.NavigationComponent
import ru.kyamshanov.mission.core.navigation.common.utils.di
import ru.kyamshanov.mission.core.navigation.common.utils.diInternal
import ru.kyamshanov.mission.core.navigation.common.utils.getValue
import ru.kyamshanov.mission.project_view.impl.ui.model.ProjectScreenState


internal interface ProjectViewModel {

    val screenStateFlow: Value<ProjectScreenState>
    fun createTask()
    fun openTask(taskId: String)
    fun onBack()
    fun setTitle(title: String)
    fun setDescription(description: String)
    fun saveChanges()
    fun clickOnPoints()
    fun clickOnParticipants()
}

internal class ProjectUiComponent(
    private val projectId: ProjectId,
    private val context: ComponentContext
) : ComponentContext by context {

    private val internalApi by requireNotNull(instanceKeeper.diInternal<EditProjectComponent, EditProjectModuleComponent>())
    private val navigationComponent by requireNotNull(instanceKeeper.di<NavigationComponent>())

    val viewModel: ProjectViewModel =
        instanceKeeper.getOrCreate(::ProjectRetainedInstance)

    val taskStagePresentUseCase get() = internalApi.taskStagePresentUseCase

    private val projectInteractor get() = internalApi.projectInteractor
    private val navigator get() = navigationComponent.navigator

    private inner class ProjectRetainedInstance : InstanceKeeper.Instance,
        ProjectViewModel {

        private val viewModelScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
        private val _screenStateFlow =
            MutableValue(ProjectScreenState())

        override val screenStateFlow = _screenStateFlow

        init {
            viewModelScope.launch(Dispatchers.Default) {
                projectInteractor.editableSchemeStateFlow.collect { editingScheme ->
                    _screenStateFlow.update { state -> state.copy(editingScheme = editingScheme) }
                }
            }
        }

        init {
            viewModelScope.launch(Dispatchers.Default) {
                projectInteractor.fetchProjectById(projectId)
                    .onSuccess { projectInfo ->
                        _screenStateFlow.update { state ->
                            state.copy(
                                loading = false,
                                projectInfo = projectInfo,
                                totalPointsInfo = TotalPointsInfo(
                                    projectInfo.title,
                                    projectInfo.tasks.map { it.toStagePointInfo() }
                                ),
                            )
                        }
                    }
                    .onFailure {
                        Napier.e(tag = LOG_TAG, message = "Get project error", throwable = it)
                        //  _screenStateFlow.value = _screenStateFlow.value.copy(loading = false)
                    }
            }
        }

        override fun createTask() {
            /*taskCreationLauncher.launch(
                ProjectId(projectId),
                screenStateFlow.value.projectInfo?.title.orEmpty()
            )*/
        }

        override fun openTask(taskId: String) {
            /*   screenStateFlow.value.projectInfo?.let { projectInfo ->
                   taskViewLauncher.get().launch(
                       projectTitle = projectInfo.title,
                       taskId = TaskId(taskId),
                       projectId = ProjectId(projectInfo.id)
                   )
               }*/
        }

        override fun onBack() {
            navigator.exit()
        }

        override fun setTitle(title: String) {
            projectInteractor.setTitle(title)
                .onSuccess { projectInfo -> _screenStateFlow.update { it.copy(projectInfo = projectInfo) } }
        }

        override fun setDescription(description: String) {
            projectInteractor.setDescription(description)
                .onSuccess { projectInfo -> _screenStateFlow.update { it.copy(projectInfo = projectInfo) } }
        }

        override fun saveChanges() {
            viewModelScope.launch {
                projectInteractor.saveChanges()
                    .onFailure {
                        Napier.e("Save project error", it, LOG_TAG)
                    }
            }
        }

        override fun clickOnPoints() {
            navigator.navigateTo(TotalPointsViewScreen(screenStateFlow.value.totalPointsInfo))
        }

        override fun clickOnParticipants() {
            screenStateFlow.value.projectInfo?.let { projectInfo ->
                navigator.navigateTo(ParticipantsListScreen(projectInfo.toSlim()))
            }
        }

        override fun onDestroy() {
            viewModelScope.cancel()
        }
    }

    private companion object {

        const val LOG_TAG = "ProjectViewModel"
    }
}