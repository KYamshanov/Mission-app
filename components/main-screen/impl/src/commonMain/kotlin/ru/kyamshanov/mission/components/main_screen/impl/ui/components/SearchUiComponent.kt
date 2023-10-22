package ru.kyamshanov.mission.components.main_screen.impl.ui.components

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.kyamshanov.mission.components.main_screen.impl.ui.models.ProjectInfoSlim
import ru.kyamshanov.mission.components.main_screen.impl.ui.models.SlimItem
import ru.kyamshanov.mission.components.main_screen.impl.ui.models.TaskInfoSlim
import ru.kyamshanov.mission.components.points.api.common.TaskPriority
import ru.kyamshanov.mission.components.points.api.common.TaskStatus
import ru.kyamshanov.mission.components.points.api.di.TaskComponent
import ru.kyamshanov.mission.components.points.api.domain.TaskRepository
import ru.kyamshanov.mission.components.points.api.presentation.navigation.TaskLauncher
import ru.kyamshanov.mission.core.navigation.api.Navigator
import ru.kyamshanov.mission.core.navigation.api.di.NavigationComponent
import ru.kyamshanov.mission.core.navigation.common.utils.di
import ru.kyamshanov.mission.core.navigation.common.utils.getValue

internal interface SearchViewModel {

    val viewState: Value<State>

    fun searchByName(phrase: String)

    fun openItem(itemId: String)

    fun clear()

    data class State(
        val items: List<SlimItem>?,
    ) {

        companion object {

            val Uninitialized = State(null)
        }

    }
}

internal class SearchProjectUiComponent(
    private val context: ComponentContext
) : ComponentContext by context {

    val viewModel: SearchViewModel =
        instanceKeeper.getOrCreate {
            println("NewInstance : SearchRetainedInstance")
            val taskComponent: TaskComponent by requireNotNull(instanceKeeper.di())

            SearchRetainedInstance(
                taskComponent.taskRepository,
                taskComponent.launcher
            )
        }

    private class SearchRetainedInstance(
        private val taskRepository: TaskRepository,
        private val taskLauncher: TaskLauncher,
    ) : InstanceKeeper.Instance,
        SearchViewModel {

        val viewModelScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

        override val viewState get() = _viewState

        private val _viewState = MutableValue(SearchViewModel.State.Uninitialized)
        private var searchJob: Job? = null


        override fun searchByName(phrase: String) {
            if (phrase.isBlank()) {
                clear()
                return
            }

            searchJob?.cancel()
            searchJob = viewModelScope.launch {
                delay(300L)
                taskRepository.search(phrase)
                    .onSuccess { result ->
                        val tasks = result.map {
                            TaskInfoSlim(
                                it.id,
                                it.title,
                                it.status == TaskStatus.COMPLETED,
                                isHighPriority = it.priority == TaskPriority.PRIMARY,
                                isLowPriority = it.priority == TaskPriority.LOW
                            )
                        }
                        _viewState.update { it.copy(items = tasks) }
                    }
            }
        }

        override fun openItem(itemId: String) {
            val item = _viewState.value.items?.find { it.id == itemId } ?: return
            when (item) {
                is ProjectInfoSlim -> Unit
                is TaskInfoSlim -> taskLauncher.launchEditing(itemId)
            }
        }

        override fun clear() {
            searchJob?.cancel()
            _viewState.update { it.copy(items = null) }
        }

        override fun onDestroy() {
            viewModelScope.cancel()
        }

    }
}
