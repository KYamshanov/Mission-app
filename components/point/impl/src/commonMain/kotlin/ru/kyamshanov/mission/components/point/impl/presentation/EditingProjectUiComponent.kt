package ru.kyamshanov.mission.components.point.impl.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import ru.kyamshanov.mission.components.point.impl.di.TaskModuleComponent
import ru.kyamshanov.mission.components.point.impl.domain.interactor.ProjectInteractor
import ru.kyamshanov.mission.components.point.impl.domain.models.LabelModel
import ru.kyamshanov.mission.components.points.api.common.*
import ru.kyamshanov.mission.components.points.api.di.TaskComponent
import ru.kyamshanov.mission.core.navigation.api.Navigator
import ru.kyamshanov.mission.core.navigation.api.di.NavigationComponent
import ru.kyamshanov.mission.core.navigation.common.utils.di
import ru.kyamshanov.mission.core.navigation.common.utils.diInternal
import ru.kyamshanov.mission.core.navigation.common.utils.getValue


internal interface EditingProjectViewModel {

    val viewState: Value<State>

    fun onBack()
    fun delete()

    fun setTitle(title: String)

    fun setDescription(description: String)

    fun saveChanges()
    fun startEditingDescription()
    fun startEditingTitle()


    data class State(
        val title: String,
        val description: String?,
        val loading: Boolean,
        val saveChangesButtonAvailable: Boolean,
        val isTitleEditing: Boolean?,
        val isDescriptionEditing: Boolean?,
        val labels: List<LabelModel>
    ) {
        fun isInitialized() = this !== Uninitialized

        companion object {
            val Uninitialized = State("", "", false, false, null, null, emptyList())
        }
    }
}

internal class EditingProjectUiComponent(
    val taskId: TaskId,
    private val context: ComponentContext
) : ComponentContext by context {

    val viewModel: EditingProjectViewModel =
        instanceKeeper.getOrCreate {
            val navigationComponent by requireNotNull(instanceKeeper.di<NavigationComponent>())
            val internalApi by requireNotNull(instanceKeeper.diInternal<TaskComponent, TaskModuleComponent>())

            EditingProjectRetainedInstance(
                projectId = taskId,
                navigator = navigationComponent.navigator,
                internalApi.projectInteractor,
            )
        }

    private class EditingProjectRetainedInstance(
        private val projectId: ProjectId,
        private val navigator: Navigator,
        private val interactor: ProjectInteractor,
    ) : InstanceKeeper.Instance,
        EditingProjectViewModel {

        override val viewState: Value<EditingProjectViewModel.State> get() = _projectState

        private var initialTitle: String? = null
        private var initialDescription: String? = null

        override fun onBack() {
            navigator.exit()
        }

        override fun delete() {
            viewModelScope.launch {
                _projectState.update { it.copy(loading = true) }
                interactor.delete(projectId)
                    .onSuccess {
                        _projectState.update { it.copy(loading = false) }
                        navigator.exit()
                    }
                    .onFailure { Napier.e(it, "Project editing") { "error in delete task process" } }
            }
        }

        override fun setTitle(title: String) {
            _projectState.update { it.copy(title = title, saveChangesButtonAvailable = title != initialTitle) }
        }

        override fun setDescription(description: String) {
            _projectState.update {
                it.copy(
                    description = description,
                    saveChangesButtonAvailable = description != initialDescription
                )
            }
        }

        override fun saveChanges() {
            viewModelScope.launch {
                val title = _projectState.value.title.takeIf { it != initialTitle }
                val description = _projectState.value.description.takeIf { it != initialTitle }
                interactor.updateProject(projectId, title, description)
                    .also { _projectState.update { it.copy(saveChangesButtonAvailable = false) } }
                    .onSuccess { onChangesSaved() }
            }
        }

        override fun startEditingDescription() {
            _projectState.update { it.copy(isDescriptionEditing = true) }
        }

        override fun startEditingTitle() {
            _projectState.update { it.copy(isTitleEditing = true) }
        }

        private val _projectState = MutableValue(EditingProjectViewModel.State.Uninitialized)
        private val viewModelScope = CoroutineScope(kotlinx.coroutines.Dispatchers.Main + SupervisorJob())


        init {
            viewModelScope.launch {
                interactor.fetchProject(projectId)
                    .onSuccess { result ->
                        initialTitle = result.title
                        initialDescription = result.description
                        _projectState.update {
                            it.copy(
                                title = result.title,
                                description = result.description,
                                isDescriptionEditing = if (result.editingRules.isDescriptionEditable) false else null,
                                isTitleEditing = if (result.editingRules.isTitleEditable) false else null,
                                labels = result.labels
                            )
                        }
                    }
                    .onFailure { Napier.e(it) { "Fetching error" } }
            }
        }


        private fun onChangesSaved() {
            val projectState = _projectState.value
            initialTitle = projectState.title
            initialDescription = projectState.description
            _projectState.update {
                it.copy(
                    title = projectState.title,
                    isDescriptionEditing = false.takeIf { projectState.isDescriptionEditing != null },
                    isTitleEditing = false.takeIf { projectState.isTitleEditing != null },
                )
            }
        }


        override fun onDestroy() {
            viewModelScope.cancel()
        }

        companion object {

            private const val PREFIX_SEPARATOR = ":"
        }
    }
}
