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
import ru.kyamshanov.mission.components.point.impl.presentation.screens.EditingTaskScreen
import ru.kyamshanov.mission.components.points.api.di.TaskComponent
import ru.kyamshanov.mission.core.navigation.api.Navigator
import ru.kyamshanov.mission.core.navigation.api.di.NavigationComponent
import ru.kyamshanov.mission.core.navigation.common.utils.di
import ru.kyamshanov.mission.core.navigation.common.utils.diInternal
import ru.kyamshanov.mission.core.navigation.common.utils.getValue


internal interface CreationTaskViewModel {

    val viewState: Value<State>

    fun onBack()
    fun setTitle(s: String)
    fun setDescription(s: String)
    fun create()

    data class State(
        val title: String,
        val description: String,
        val loading: Boolean
    ) {

        constructor() : this("", "", false)
    }
}

internal class CreationTaskUiComponent(
    private val context: ComponentContext
) : ComponentContext by context {

    val viewModel: CreationTaskViewModel =
        instanceKeeper.getOrCreate {
            val navigationComponent by requireNotNull(instanceKeeper.di<NavigationComponent>())
            val internalApi by requireNotNull(instanceKeeper.diInternal<TaskComponent, TaskModuleComponent>())

            CreationTaskRetainedInstance(
                navigator = navigationComponent.navigator,
                interactor = internalApi.interactor,
            )
        }

    private class CreationTaskRetainedInstance(
        private val navigator: Navigator,
        private val interactor: TaskInteractor
    ) : InstanceKeeper.Instance,
        CreationTaskViewModel {

        override val viewState: Value<CreationTaskViewModel.State> get() = _viewState
        override fun onBack() {
            navigator.exit()
        }

        override fun setTitle(s: String) {
            _viewState.update { it.copy(title = s) }
        }

        override fun setDescription(s: String) {
            _viewState.update { it.copy(description = s) }
        }

        override fun create() {
            val value = _viewState.value
            viewModelScope.launch {
                _viewState.update { it.copy(loading = true) }
                interactor.create(value.title, value.description)
                    .also { _viewState.update { it.copy(loading = false) } }
                    .onSuccess {
                        navigator.replaceTo(EditingTaskScreen(it))
                    }
                    .onFailure { Napier.e(it, "Creation") { "error in creation process" } }
            }
        }

        private val _viewState = MutableValue(CreationTaskViewModel.State())
        private val viewModelScope = CoroutineScope(kotlinx.coroutines.Dispatchers.Main + SupervisorJob())


        override fun onDestroy() {
            viewModelScope.cancel()
        }
    }
}
