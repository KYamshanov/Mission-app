package ru.kyamshanov.mission.components.main_screen.impl.ui.components

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.arkivanov.essenty.lifecycle.doOnStart
import io.github.aakira.napier.Napier
import kotlinx.coroutines.*
import ru.kyamshanov.mission.components.main_screen.impl.ui.models.ProjectInfoSlim
import ru.kyamshanov.mission.components.main_screen.impl.ui.models.SlimItem
import ru.kyamshanov.mission.components.main_screen.impl.ui.models.TaskInfoSlim
import ru.kyamshanov.mission.components.points.api.common.LabelModel
import ru.kyamshanov.mission.components.points.api.common.TaskPriority
import ru.kyamshanov.mission.components.points.api.common.TaskStatus
import ru.kyamshanov.mission.components.points.api.di.TaskComponent
import ru.kyamshanov.mission.components.points.api.domain.LabelsRepository
import ru.kyamshanov.mission.components.points.api.domain.SearchInteractor
import ru.kyamshanov.mission.components.points.api.domain.TaskRepository
import ru.kyamshanov.mission.components.points.api.presentation.navigation.ProjectLauncher
import ru.kyamshanov.mission.components.points.api.presentation.navigation.TaskLauncher
import ru.kyamshanov.mission.core.navigation.common.utils.di
import ru.kyamshanov.mission.core.navigation.common.utils.getValue

internal interface SearchViewModel {

    val viewState: Value<State>

    fun setSearchPhrase(phrase: String)

    fun setSearchLabel(labelModel: LabelModel)

    fun clickOnLabels()

    fun openItem(taskInfoSlim: TaskInfoSlim)
    fun openItem(projectInfoSlim: ProjectInfoSlim)

    fun clear()

    data class State(
        val searchPhrase: String,
        val selectedLabel: LabelModel,
        val availableLabels: List<LabelModel>,
        val tasks: List<TaskInfoSlim>?,
        val projects: List<ProjectInfoSlim>?,
    ) {

        companion object {

            val DefaultLabelModel = LabelModel("", "Всё", 0xFF888888)

            val Uninitialized = State("", DefaultLabelModel, emptyList(), null, null)
        }

    }
}

internal class SearchProjectUiComponent(
    private val context: ComponentContext
) : ComponentContext by context {

    val viewModel: SearchViewModel get() = _viewModel

    private val _viewModel = instanceKeeper.getOrCreate {
        println("NewInstance : SearchRetainedInstance")
        val taskComponent: TaskComponent by requireNotNull(instanceKeeper.di())

        SearchRetainedInstance(
            taskComponent.launcher,
            taskComponent.searchInteractor,
            taskComponent.labelsRepository,
            taskComponent.projectLauncher,
        )
    }

    init {
        lifecycle.doOnStart(isOneTime = true) {
            _viewModel.fetchLabels()
        }
    }

    private class SearchRetainedInstance(
        private val taskLauncher: TaskLauncher,
        private val searchInteractor: SearchInteractor,
        private val labelsRepository: LabelsRepository,
        private val projectLauncher: ProjectLauncher,
    ) : InstanceKeeper.Instance,
        SearchViewModel {

        val viewModelScope = MainScope()

        override val viewState get() = _viewState

        private val _viewState = MutableValue(SearchViewModel.State.Uninitialized)
        private var searchJob: Job? = null
        private var labelsDiff: Deferred<Result<List<LabelModel>>> = viewModelScope.async { labelsRepository.getAll() }

        fun fetchLabels() {
            if (labelsDiff.isActive.not()) {
                labelsDiff = viewModelScope.async { labelsRepository.getAll() }
            }
        }

        override fun setSearchPhrase(phrase: String) {
            if (phrase.isNotEmpty() && phrase.isBlank()) return

            _viewState.update { it.copy(searchPhrase = phrase) }
            search()
        }

        private fun search() {
            val phrase = _viewState.value.searchPhrase
            val labels = _viewState.value.selectedLabel.takeIf { it != SearchViewModel.State.DefaultLabelModel }
                ?.let { listOf(it) }.orEmpty()

            if (phrase.isBlank() && labels.isEmpty()) {
                clear()
                return
            }

            searchJob?.cancel()
            searchJob = viewModelScope.launch {
                delay(300L)
                searchInteractor.search(
                    phrase = phrase,
                    labels = labels
                )
                    .onSuccess { result ->
                        val tasks = result.tasks.map {
                            TaskInfoSlim(
                                it.id,
                                it.title,
                                it.status == TaskStatus.COMPLETED,
                                isHighPriority = it.priority == TaskPriority.PRIMARY,
                                isLowPriority = it.priority == TaskPriority.LOW
                            )
                        }
                        val projects = result.projects.map {
                            ProjectInfoSlim(
                                it.id,
                                it.title,
                                false
                            )
                        }
                        _viewState.update { it.copy(tasks = tasks, projects = projects) }
                    }
            }
        }

        override fun setSearchLabel(labelModel: LabelModel) {
            _viewState.update { it.copy(selectedLabel = labelModel, availableLabels = emptyList()) }
            search()
        }

        override fun clickOnLabels() {
            viewModelScope.launch {
                if (_viewState.value.availableLabels.isNotEmpty()) {
                    _viewState.update { it.copy(availableLabels = emptyList()) }
                } else {
                    labelsDiff.await()
                        .onSuccess { labels ->
                            _viewState.update { it.copy(availableLabels = listOf(SearchViewModel.State.DefaultLabelModel) + labels) }
                        }
                        .onFailure { Napier.e(it) { "Error loading labels" } }
                }
            }
        }

        override fun openItem(projectInfoSlim: ProjectInfoSlim) {
            projectLauncher.launchEditing(projectInfoSlim.id)
        }

        override fun openItem(taskInfoSlim: TaskInfoSlim) {
            taskLauncher.launchEditing(taskInfoSlim.id)
        }

        override fun clear() {
            searchJob?.cancel()
            _viewState.update {
                it.copy(
                    tasks = null,
                    projects = null,
                    searchPhrase = "",
                    selectedLabel = SearchViewModel.State.DefaultLabelModel
                )
            }
        }

        override fun onDestroy() {
            viewModelScope.cancel()
        }

    }
}
