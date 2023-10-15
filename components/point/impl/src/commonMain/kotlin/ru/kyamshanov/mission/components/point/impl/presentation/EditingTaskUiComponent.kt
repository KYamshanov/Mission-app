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
import ru.kyamshanov.mission.components.point.impl.domain.interactor.TaskInteractor
import ru.kyamshanov.mission.components.point.impl.domain.usecase.GetTaskUseCase
import ru.kyamshanov.mission.components.points.api.common.TaskId
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

    data class State(
        val title: String,
        val description: String,
        val loading: Boolean
    ) {
        fun isInitialized() = this != Uninitialized

        companion object {
            val Uninitialized = State("", "", false)
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

        private val _taskState = MutableValue(EditingTaskViewModel.State.Uninitialized)
        private val viewModelScope = CoroutineScope(kotlinx.coroutines.Dispatchers.Main + SupervisorJob())


        init {
            viewModelScope.launch {
                getTaskUseCase.fetch(taskId)
                    .onSuccess { result ->
                        _taskState.update {
                            it.copy(
                                title = result.title,
                                description = result.description
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
