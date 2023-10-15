package ru.kyamshanov.mission.components.main_screen.impl.ui.components

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.kyamshanov.mission.components.main_screen.impl.ui.models.SlimItem
import ru.kyamshanov.mission.components.points.api.di.TaskComponent
import ru.kyamshanov.mission.components.project.api.common.ProjectInfoSlim
import ru.kyamshanov.mission.components.project.api.editing.di.EditProjectComponent
import ru.kyamshanov.mission.components.project.api.search.di.SearchProjectComponent
import ru.kyamshanov.mission.components.project.api.search.domain.models.PageIndex
import ru.kyamshanov.mission.core.navigation.common.utils.di
import ru.kyamshanov.mission.core.navigation.common.utils.getValue

internal interface SearchViewModel {

    val viewState: Value<State>

    fun searchByName(projectName: String)

    fun openItem(itemId: String)

    data class State(
        val items: List<SlimItem>,
    ) {

        fun isInitialized() = this !== Uninitialized

        companion object {

            val Uninitialized = State(emptyList())
        }

    }
}

internal class SearchProjectUiComponent(
    private val context: ComponentContext
) : ComponentContext by context {

    val uiComponent: SearchViewModel =
        instanceKeeper.getOrCreate(::SearchRetainedInstance)

    private val searchProjectComponent: SearchProjectComponent by requireNotNull(instanceKeeper.di())
    private val projectComponent: EditProjectComponent by requireNotNull(instanceKeeper.di())
    private val taskComponent: TaskComponent by requireNotNull(instanceKeeper.di())

    private inner class SearchRetainedInstance : InstanceKeeper.Instance,
        SearchViewModel {

        val viewModelScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

        override val viewState get() = _projectsState

        private val _projectsState = MutableValue(SearchViewModel.State.Uninitialized)
        private val searchProjectUseCase
            get() = requireNotNull(searchProjectComponent).searchProjectUseCase
        private var searchJob: Job? = null


        override fun searchByName(projectName: String) {
            //TODO("Переписать")
            /* searchJob?.cancel()
             searchJob = viewModelScope.launch {
                 delay(1000L)
                 searchProjectUseCase.searchByName(projectName, PageIndex(0, PAGE_SIZE))
                     .onSuccess {
                         _projectsState.value = it
                     }
             }*/
        }

        override fun openItem(itemId: String) {
            TODO("Реализовать")
        }

        override fun onDestroy() {
            viewModelScope.cancel()
        }

    }

    companion object {

        private const val PAGE_SIZE = 50
    }
}
