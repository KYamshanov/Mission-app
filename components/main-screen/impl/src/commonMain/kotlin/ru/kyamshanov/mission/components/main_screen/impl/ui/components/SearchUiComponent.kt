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
import ru.kyamshanov.mission.components.project.api.common.ProjectInfoSlim
import ru.kyamshanov.mission.components.project.api.search.di.SearchProjectComponent
import ru.kyamshanov.mission.components.project.api.search.domain.models.PageIndex
import ru.kyamshanov.mission.core.navigation.common.utils.di
import ru.kyamshanov.mission.core.navigation.common.utils.getValue

internal interface SearchProjectViewModel {

    val projectsState: Value<List<ProjectInfoSlim>>

    fun searchByName(projectName: String)

    fun openProject(projectId: String)
}

internal class SearchProjectUiComponent(
    private val context: ComponentContext
) : ComponentContext by context {

    val uiComponent: SearchProjectViewModel =
        instanceKeeper.getOrCreate(::SearchProjectRetainedInstance)

    private val searchProjectComponent: SearchProjectComponent by requireNotNull(instanceKeeper.di())

    private inner class SearchProjectRetainedInstance : InstanceKeeper.Instance,
        SearchProjectViewModel {

        val viewModelScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

        override val projectsState get() = _projectsState

        private val _projectsState = MutableValue(emptyList<ProjectInfoSlim>())
        private val searchProjectUseCase
            get() = requireNotNull(searchProjectComponent).searchProjectUseCase
        private var searchJob: Job? = null

        init {
            viewModelScope.launch {
                searchProjectUseCase.findAll(PageIndex(0, PAGE_SIZE))
                    .onSuccess {
                        _projectsState.value = it
                    }
            }
        }


        override fun searchByName(projectName: String) {
            searchJob?.cancel()
            searchJob = viewModelScope.launch {
                delay(1000L)
                searchProjectUseCase.searchByName(projectName, PageIndex(0, PAGE_SIZE))
                    .onSuccess {
                        _projectsState.value = it
                    }
            }
        }

        override fun openProject(projectId: String) {
            //projectLauncher.launch(projectId = projectId)
        }

        override fun onDestroy() {
            viewModelScope.cancel()
        }

    }

    companion object {

        private const val PAGE_SIZE = 50
    }
}
