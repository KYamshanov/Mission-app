package ru.kyamshanov.mission.components.main_screen.impl.ui.components

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
import ru.kyamshanov.mission.components.main_screen.impl.ui.models.SlimItem
import ru.kyamshanov.mission.components.main_screen.impl.ui.models.TaskInfoSlim
import ru.kyamshanov.mission.components.points.api.common.TaskPriority
import ru.kyamshanov.mission.components.points.api.common.TaskSlim
import ru.kyamshanov.mission.components.points.api.common.TaskStatus
import ru.kyamshanov.mission.components.points.api.common.TaskType
import ru.kyamshanov.mission.components.points.api.di.TaskComponent
import ru.kyamshanov.mission.components.points.api.domain.TaskRepository
import ru.kyamshanov.mission.components.points.api.presentation.navigation.TaskLauncher
import ru.kyamshanov.mission.core.navigation.common.utils.di
import ru.kyamshanov.mission.core.navigation.common.utils.getValue

internal interface FrontViewModel {

    val viewState: Value<State>

    fun openItem(itemId: String)

    data class State(
        val initialized: Boolean,
        val todaysTasks: List<SlimItem>,
        val weeksTasks: List<SlimItem>,
        val otherTasks: List<SlimItem>,
    ) {

        companion object {

            val Uninitialized = State(false, emptyList(), emptyList(), emptyList())
        }

    }
}

internal class FrontUiComponent(
    private val context: ComponentContext
) : ComponentContext by context {

    val uiComponent: FrontViewModel =
        instanceKeeper.getOrCreate {
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
        FrontViewModel {

        val viewModelScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

        override val viewState get() = _viewState
        override fun openItem(itemId: String) {
            if (tasks.find { it.id == itemId } != null) {
                taskLauncher.launchEditing(itemId)
            }
        }

        private val _viewState = MutableValue(FrontViewModel.State.Uninitialized)

        private var tasks = emptyList<TaskSlim>()
            set(value) {
                field = value
                val todays = mutableListOf<TaskInfoSlim>()
                val weeks = mutableListOf<TaskInfoSlim>()
                val other = mutableListOf<TaskInfoSlim>()
                value.forEach {
                    val slimItem = TaskInfoSlim(
                        it.id,
                        it.title,
                        it.status == TaskStatus.COMPLETED,
                        isHighPriority = it.priority == TaskPriority.PRIMARY,
                        isLowPriority = it.priority == TaskPriority.LOW
                    )
                    when (it.type) {
                        TaskType.TODAYS -> todays.add(slimItem)
                        TaskType.WEEKS -> weeks.add(slimItem)
                        null -> other.add(slimItem)
                    }
                }


                _viewState.update {
                    it.copy(
                        initialized = true,
                        todaysTasks = todays.sortTasks(),
                        weeksTasks = weeks.sortTasks(),
                        otherTasks = other.sortTasks()
                    )
                }
            }

        private fun MutableList<TaskInfoSlim>.sortTasks(): List<TaskInfoSlim> {
            sortWith(compareBy<TaskInfoSlim> {
                it.isCompleted
            }.thenByDescending { it.isHighPriority }.thenBy { it.isLowPriority }.thenBy { it.title.lowercase() })
            return this
        }

        init {
            viewModelScope.launch {
                taskRepository.getAll()
                    .onSuccess { tasks = it }
                    .onFailure { Napier.e(it, "FrontUI") { "Error in getting tasks" } }
            }
        }

        override fun onDestroy() {
            viewModelScope.cancel()
        }

    }

    companion object {

        private const val COMPLETED_GROUP_KEY = "completed"
        private const val PRIORITY_GROUP_KEY = "priority"
    }
}