package ru.kyamshanov.mission.components.point.impl.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import io.github.aakira.napier.Napier
import kotlinx.coroutines.*
import ru.kyamshanov.mission.components.point.impl.di.TaskModuleComponent
import ru.kyamshanov.mission.components.point.impl.domain.interactor.LabelInteractor
import ru.kyamshanov.mission.components.point.impl.domain.interactor.TaskInteractor
import ru.kyamshanov.mission.components.points.api.common.LabelModel
import ru.kyamshanov.mission.components.point.impl.domain.usecase.GetTaskUseCase
import ru.kyamshanov.mission.components.points.api.common.TaskId
import ru.kyamshanov.mission.components.points.api.common.TaskPriority
import ru.kyamshanov.mission.components.points.api.common.TaskStatus
import ru.kyamshanov.mission.components.points.api.common.TaskType
import ru.kyamshanov.mission.components.points.api.di.TaskComponent
import ru.kyamshanov.mission.core.navigation.api.Navigator
import ru.kyamshanov.mission.core.navigation.api.di.NavigationComponent
import ru.kyamshanov.mission.core.navigation.common.utils.di
import ru.kyamshanov.mission.core.navigation.common.utils.diInternal
import ru.kyamshanov.mission.core.navigation.common.utils.getValue


internal interface EditingTaskViewModel {

    val viewState: Value<State>

    fun onBack()
    fun delete()
    fun todays()

    fun weeks()

    fun completed()

    fun inProgress()

    fun resetStatus()

    /**
     * Восстановить статус и удалить тип
     */
    fun hardReset()

    fun priority()

    fun medium()

    fun low()

    fun setTitle(title: String)

    fun setDescription(description: String)

    fun saveChanges()

    fun startEditingTitle()

    fun startEditingDescription()

    fun selectLabel(labelId: String)

    fun startEditingLabels()

    fun saveLabels()

    data class State(
        val toolBarTitle: String,
        val title: String,
        val description: String,
        val loading: Boolean,
        val type: TaskType?,
        val status: TaskStatus,
        val priority: TaskPriority?,
        val saveChangesButtonAvailable: Boolean,
        val isTitleEditing: Boolean?,
        val isDescriptionEditing: Boolean?,
        val descriptionVisible: Boolean,
        val selectedLabels: List<LabelModel>,
        val labels: Map<LabelModel, Boolean>,
        val isSaveLabelsAvailable: Boolean,
        val isSettingLabelsAvailable: Boolean,
    ) {
        fun isInitialized() = this !== Uninitialized

        companion object {
            val Uninitialized =
                State(
                    toolBarTitle = "",
                    title = "",
                    description = "",
                    loading = false,
                    type = null,
                    status = TaskStatus.CREATED,
                    priority = null,
                    saveChangesButtonAvailable = false,
                    isTitleEditing = false,
                    isDescriptionEditing = false,
                    descriptionVisible = false,
                    selectedLabels = emptyList(),
                    labels = emptyMap(),
                    isSaveLabelsAvailable = false,
                    isSettingLabelsAvailable = true
                )
        }
    }
}

internal class EditingTaskUiComponent(
    val taskId: TaskId,
    private val context: ComponentContext
) : ComponentContext by context {

    val viewModel: EditingTaskViewModel =
        instanceKeeper.getOrCreate {
            val navigationComponent by requireNotNull(instanceKeeper.di<NavigationComponent>())
            val internalApi by requireNotNull(instanceKeeper.diInternal<TaskComponent, TaskModuleComponent>())

            EditingTaskRetainedInstance(
                taskId = taskId,
                navigator = navigationComponent.navigator,
                getTaskUseCase = internalApi.getTaskUseCase,
                internalApi.interactor,
                labelInteractor = internalApi.labelInteractor
            )
        }

    private class EditingTaskRetainedInstance(
        private val taskId: TaskId,
        private val navigator: Navigator,
        private val getTaskUseCase: GetTaskUseCase,
        private val interactor: TaskInteractor,
        private val labelInteractor: LabelInteractor
    ) : InstanceKeeper.Instance,
        EditingTaskViewModel {

        override val viewState: Value<EditingTaskViewModel.State> get() = _taskState

        private var initialTitle: String? = null
        private var initialDescription: String? = null
        private var initialLabels: Map<LabelModel, Boolean>? = null
            set(value) {
                field = value
                _taskState.update {
                    it.copy(selectedLabels = value?.mapNotNull { if (it.value) it.key else null }.orEmpty())
                }
            }
        private var taskPrefix: String? = null

        override fun onBack() {
            navigator.exit()
        }

        override fun delete() {
            viewModelScope.launch {
                _taskState.update { it.copy(loading = true) }
                interactor.delete(taskId)
                    .onSuccess {
                        _taskState.update { it.copy(loading = false) }
                        navigator.exit()
                    }
                    .onFailure { Napier.e(it, "Editing") { "error in delete task process" } }
            }
        }

        override fun todays() {
            viewModelScope.launch {
                _taskState.update { it.copy(loading = true) }
                val updateType = TaskType.TODAYS.takeIf { _taskState.value.type != it }
                interactor.setType(taskId, updateType)
                    .onSuccess { _taskState.update { it.copy(type = updateType) } }
                    .onFailure { Napier.e(it, "Editing") { "error in update type of task process" } }
                _taskState.update { it.copy(loading = false) }
            }
        }

        override fun weeks() {
            viewModelScope.launch {
                _taskState.update { it.copy(loading = true) }
                val updateType = TaskType.WEEKS.takeIf { _taskState.value.type != it }
                interactor.setType(taskId, updateType)
                    .onSuccess { _taskState.update { it.copy(type = updateType) } }
                    .onFailure { Napier.e(it, "Editing") { "error in update type of task process" } }
                _taskState.update { it.copy(loading = false) }
            }
        }

        override fun completed() {
            viewModelScope.launch {
                _taskState.update { it.copy(loading = true) }
                interactor.setStatus(taskId, TaskStatus.COMPLETED)
                    .also { _taskState.update { it.copy(loading = false) } }
                    .onSuccess { navigator.exit() }
                    .onFailure { Napier.e(it, "Editing") { "error in update status of task process" } }
            }
        }

        override fun inProgress() {
            viewModelScope.launch {
                _taskState.update { it.copy(loading = true) }
                interactor.setStatus(taskId, TaskStatus.IN_PROCESSING)
                    .onSuccess {
                        _taskState.update { it.copy(loading = false) }
                        navigator.exit()
                    }
                    .onFailure { Napier.e(it, "Editing") { "error in update status of task process" } }
            }
        }

        override fun resetStatus() {
            viewModelScope.launch {
                _taskState.update { it.copy(loading = true) }
                interactor.setStatus(taskId, TaskStatus.CREATED)
                    .also { _taskState.update { it.copy(loading = false) } }
                    .onSuccess { navigator.exit() }
                    .onFailure { Napier.e(it, "Editing") { "error in update status of task process" } }
            }
        }

        override fun hardReset() {
            viewModelScope.launch {
                _taskState.update { it.copy(loading = true) }
                interactor.setStatus(taskId, TaskStatus.CREATED)
                    .onSuccess { _taskState.update { it.copy(status = TaskStatus.CREATED) } }
                    .onFailure { Napier.e(it, "Editing") { "error in update status of task process" } }
                interactor.setType(taskId, null)
                    .onSuccess { _taskState.update { it.copy(type = null) } }
                    .onFailure { Napier.e(it, "Editing") { "error in update status of task process" } }
                _taskState.update { it.copy(loading = false) }
            }
        }

        override fun priority() {
            viewModelScope.launch {
                _taskState.update { it.copy(loading = true) }
                val priority = TaskPriority.PRIMARY.takeIf { _taskState.value.priority != it }
                interactor.setPriority(taskId, priority)
                    .also { _taskState.update { it.copy(loading = false) } }
                    .onSuccess { _taskState.update { it.copy(priority = priority) } }
                    .onFailure { Napier.e(it, "Editing") { "error in update priority of task" } }
            }
        }

        override fun medium() {
            viewModelScope.launch {
                _taskState.update { it.copy(loading = true) }
                interactor.setPriority(taskId, null)
                    .also { _taskState.update { it.copy(loading = false) } }
                    .onSuccess { _taskState.update { it.copy(priority = null) } }
                    .onFailure { Napier.e(it, "Editing") { "error in update priority of task" } }
            }
        }

        override fun low() {
            viewModelScope.launch {
                _taskState.update { it.copy(loading = true) }
                val priority = TaskPriority.LOW.takeIf { _taskState.value.priority != it }
                interactor.setPriority(taskId, priority)
                    .also { _taskState.update { it.copy(loading = false) } }
                    .onSuccess { _taskState.update { it.copy(priority = priority) } }
                    .onFailure { Napier.e(it, "Editing") { "error in update priority of task" } }
            }
        }

        override fun setTitle(title: String) {
            _taskState.update { it.copy(title = title, saveChangesButtonAvailable = title != initialTitle) }
        }

        override fun setDescription(description: String) {
            _taskState.update {
                it.copy(
                    description = description,
                    saveChangesButtonAvailable = description != initialDescription
                )
            }
        }

        override fun saveChanges() {
            viewModelScope.launch {
                val title = _taskState.value.title.takeIf { it != initialTitle }
                val description = _taskState.value.description.takeIf { it != initialTitle }
                interactor.changeTask(taskId, title, description)
                    .also { _taskState.update { it.copy(saveChangesButtonAvailable = false) } }
                    .onSuccess { onChangesSaved() }
            }
        }

        override fun startEditingTitle() {
            val newTitle = initialTitle ?: return
            _taskState.update { it.copy(title = newTitle, descriptionVisible = true, isTitleEditing = true) }
        }

        override fun startEditingDescription() {
            _taskState.update { it.copy(isDescriptionEditing = true) }
        }

        override fun selectLabel(labelId: String) {
            val labels = _taskState.value.labels.toMutableMap()
            val label = labels.entries.first { it.key.id == labelId }
            labels[label.key] = !label.value
            _taskState.update { it.copy(labels = labels) }
        }

        override fun startEditingLabels() {
            if (initialLabels != null) {
                if (_taskState.value.labels.isNotEmpty()) saveLabels()
                else {
                    _taskState.update {
                        it.copy(
                            labels = initialLabels.orEmpty(),
                            isSettingLabelsAvailable = false,
                            isSaveLabelsAvailable = true
                        )
                    }
                }
            }
        }

        override fun saveLabels() {
            viewModelScope.launch {
                val labels = _taskState.value.labels
                interactor.updateLabels(
                    taskId = taskId,
                    labels = labels.mapNotNull { if (it.value) it.key else null }
                )
                    .onSuccess {
                        initialLabels = labels
                        _taskState.update {
                            it.copy(
                                labels = emptyMap(),
                                isSettingLabelsAvailable = true,
                                saveChangesButtonAvailable = false
                            )
                        }
                    }
                    .onFailure { Napier.e(it) { "Updating labels error" } }
            }
        }

        private val _taskState = MutableValue(EditingTaskViewModel.State.Uninitialized)
        private val viewModelScope = CoroutineScope(kotlinx.coroutines.Dispatchers.Main + SupervisorJob())


        init {
            viewModelScope.launch {
                getTaskUseCase.fetch(taskId)
                    .onSuccess { result ->
                        val labels =
                            labelInteractor.getAll().getOrElse { emptyList() }.associateWith { false }.toMutableMap()
                        result.labels.forEach { m ->
                            labels.entries.find { it.key.id == m.id }?.key?.also {
                                labels[it] = true
                            }
                        }
                        initialLabels = labels
                        initialTitle = result.title
                        initialDescription = result.description
                        val slitTitle = result.title.split(PREFIX_SEPARATOR, limit = 2)
                        taskPrefix = slitTitle.takeIf { it.size == 2 }?.get(0)
                        _taskState.update {
                            it.copy(
                                toolBarTitle = slitTitle[0],
                                title = (slitTitle.getOrNull(1) ?: result.title).trim(),
                                description = result.description,
                                status = result.status,
                                type = result.type,
                                priority = result.priority,
                                isDescriptionEditing = if (result.editingRules.isDescriptionEditable) false else null,
                                isTitleEditing = if (result.editingRules.isTitleEditable) false else null,
                                descriptionVisible = result.description.isNotEmpty(),
                            )
                        }
                    }
                    .onFailure { Napier.e(it) { "Fetching error" } }
            }
        }


        private fun onChangesSaved() {
            val taskState = _taskState.value
            initialTitle = taskState.title
            initialDescription = taskState.description
            val slitTitle = taskState.title.split(PREFIX_SEPARATOR, limit = 2)
            taskPrefix = slitTitle.takeIf { it.size == 2 }?.get(0)
            _taskState.update {
                it.copy(
                    toolBarTitle = slitTitle[0],
                    title = (slitTitle.getOrNull(1) ?: taskState.title).trim(),
                    isDescriptionEditing = false.takeIf { taskState.isDescriptionEditing != null },
                    isTitleEditing = false.takeIf { taskState.isTitleEditing != null },
                    descriptionVisible = taskState.description.isNotEmpty()
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
