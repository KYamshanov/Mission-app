package ru.kyamshanov.mission.components.point.impl.editing.presentation

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
import ru.kyamshanov.mission.components.point.impl.editing.di.EditingModuleComponent
import ru.kyamshanov.mission.components.points.api.common.TaskId
import ru.kyamshanov.mission.components.points.api.editing.di.EditingTaskComponent
import ru.kyamshanov.mission.core.navigation.common.utils.diInternal
import ru.kyamshanov.mission.core.navigation.common.utils.getValue


internal interface EditingTaskViewModel {

    val taskState: Value<State>

    data class State(
        val title: String? = null
    )
}

internal class EditingTaskUiComponent(
    val taskId: TaskId,
    private val context: ComponentContext
) : ComponentContext by context {

    val viewModel: EditingTaskViewModel = instanceKeeper.getOrCreate(::EditingTaskRetainedInstance)

    private val internalApi by requireNotNull(instanceKeeper.diInternal<EditingTaskComponent, EditingModuleComponent>())

    private inner class EditingTaskRetainedInstance : InstanceKeeper.Instance,
        EditingTaskViewModel {

        override val taskState: Value<EditingTaskViewModel.State> get() = _taskState

        private val _taskState = MutableValue(EditingTaskViewModel.State())
        private val viewModelScope = CoroutineScope(kotlinx.coroutines.Dispatchers.Main + SupervisorJob())


        init {
            viewModelScope.launch {
                internalApi.getTaskUseCase.fetch(taskId)
                    .onSuccess { result -> _taskState.update { it.copy(title = result.title.also { println("Title ${it}") }) } }
                    .onFailure { Napier.e(it) { "Fetching error" } }
            }
        }


        override fun onDestroy() {
            viewModelScope.cancel()
        }


    }
}
