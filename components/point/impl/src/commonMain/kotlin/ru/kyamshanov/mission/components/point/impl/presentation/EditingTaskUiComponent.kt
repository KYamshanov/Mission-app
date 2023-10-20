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
import ru.kyamshanov.mission.components.point.impl.data.model.TaskTypeDto
import ru.kyamshanov.mission.components.point.impl.di.TaskModuleComponent
import ru.kyamshanov.mission.components.point.impl.domain.interactor.TaskInteractor
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

    data class State(
        val title: String,
        val description: String,
        val loading: Boolean,
        val type: TaskType?,
        val status: TaskStatus,
        val priority: TaskPriority?
    ) {
        fun isInitialized() = this !== Uninitialized

        companion object {
            val Uninitialized = State("", "", false, null, TaskStatus.CREATED, null)
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
            )
        }

    private class EditingTaskRetainedInstance(
        private val taskId: TaskId,
        private val navigator: Navigator,
        private val getTaskUseCase: GetTaskUseCase,
        private val interactor: TaskInteractor,
    ) : InstanceKeeper.Instance,
        EditingTaskViewModel {

        override val viewState: Value<EditingTaskViewModel.State> get() = _taskState
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

        private val _taskState = MutableValue(EditingTaskViewModel.State.Uninitialized)
        private val viewModelScope = CoroutineScope(kotlinx.coroutines.Dispatchers.Main + SupervisorJob())


        init {
            viewModelScope.launch {
                getTaskUseCase.fetch(taskId)
                    .onSuccess { result ->
                        _taskState.update {
                            it.copy(
                                title = result.title,
                                description = result.description,
                                status = result.status,
                                type = result.type,
                                priority = result.priority
                            )
                        }
                    }
                    .onFailure { Napier.e(it) { "Fetching error" } }
            }
        }


        override fun onDestroy() {
            viewModelScope.cancel()
        }


    }
}
