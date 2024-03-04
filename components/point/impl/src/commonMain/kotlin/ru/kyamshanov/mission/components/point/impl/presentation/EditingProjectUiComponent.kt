package ru.kyamshanov.mission.components.point.impl.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.parcelable.Parcelable
import io.github.aakira.napier.Napier
import kotlinx.coroutines.launch
import ru.kyamshanov.mission.components.point.impl.domain.interactor.ProjectInteractor
import ru.kyamshanov.mission.components.point.impl.domain.interactor.TaskInteractor
import ru.kyamshanov.mission.components.points.api.common.LabelModel
import ru.kyamshanov.mission.components.points.api.common.TaskId
import ru.kyamshanov.mission.components.points.api.common.TaskSlim
import ru.kyamshanov.mission.components.points.api.common.TaskStatus
import ru.kyamshanov.mission.components.points.api.domain.SearchInteractor
import ru.kyamshanov.mission.components.points.api.presentation.navigation.TaskLauncher
import ru.kyamshanov.mission.core.navigation.api.Navigator
import ru.kyamshanov.mission.core.navigation.common.utils.retainedCoroutineScope
import ru.kyamshanov.mission.core.navigation.common.utils.viewState


internal interface EditingProjectViewModel {

    val viewState: Value<State>

    fun onBack()
    fun delete()

    fun setTitle(title: String)

    fun setDescription(description: String)

    fun saveChanges()
    fun startEditingDescription()
    fun startEditingTitle()

    fun setNexTaskTitle(value: String)

    fun createNewTask()

    fun openTask(taskSlim: TaskSlim)

    data class State(
        val title: String,
        val description: String?,
        val loading: Boolean,
        val saveChangesButtonAvailable: Boolean,
        val isTitleEditing: Boolean?,
        val isDescriptionEditing: Boolean?,
        val labels: List<LabelModel>,
        val newTaskTitle: String,
        val tasks: List<TaskSlim>
    ) : Parcelable {
        fun isInitialized() = this !== Uninitialized

        companion object {
            val Uninitialized = State("", "", false, false, null, null, emptyList(), "", emptyList())
        }
    }
}

internal class EditingProjectUiComponent(
    val projectId: TaskId,
    private val context: ComponentContext,
    private val navigator: Navigator,
    private val interactor: ProjectInteractor,
    private val taskInteractor: TaskInteractor,
    private val searchInteractor: SearchInteractor,
    private val taskLauncher: TaskLauncher,
) : ComponentContext by context, EditingProjectViewModel {

    private val _projectState = viewState { EditingProjectViewModel.State.Uninitialized }
    override val viewState: Value<EditingProjectViewModel.State> = _projectState

    private var initialTitle: String? = null
    private var initialDescription: String? = null

    override fun onBack() {
        navigator.exit()
    }

    override fun delete() {
        retainedCoroutineScope.launch {
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
        retainedCoroutineScope.launch {
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

    override fun setNexTaskTitle(value: String) {
        _projectState.update { it.copy(newTaskTitle = value) }
    }

    override fun createNewTask() {
        retainedCoroutineScope.launch {
            val title = _projectState.value.newTaskTitle.trim()
            val label = _projectState.value.labels.first()
            require(title.isNotBlank()) { "Title cannot be black at task title " }
            taskInteractor.create(title, "", label.id)
                .onSuccess { taskId ->
                    _projectState.update {
                        it.copy(
                            newTaskTitle = "",
                            tasks = it.tasks + listOf(TaskSlim(taskId, title, null, TaskStatus.CREATED, null, 0))
                        )
                    }
                }
                .onFailure { Napier.e(it) { "Error creation new task for project" } }
        }
    }

    override fun openTask(taskSlim: TaskSlim) {
        taskLauncher.launchEditing(taskSlim.id)
    }


    init {
        retainedCoroutineScope.launch {
            interactor.fetchProject(projectId)
                .onSuccess { result ->
                    val tasks =
                        searchInteractor.search("", listOf(result.labels.first())).getOrNull()?.tasks.orEmpty()

                    initialTitle = result.title
                    initialDescription = result.description
                    _projectState.update {
                        it.copy(
                            title = result.title,
                            description = result.description,
                            isDescriptionEditing = if (result.editingRules.isDescriptionEditable) false else null,
                            isTitleEditing = if (result.editingRules.isTitleEditable) false else null,
                            labels = result.labels,
                            tasks = tasks
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
}
