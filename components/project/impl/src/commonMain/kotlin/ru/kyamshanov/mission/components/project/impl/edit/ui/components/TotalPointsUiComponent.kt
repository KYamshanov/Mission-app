package ru.kyamshanov.mission.components.project.impl.edit.ui.components

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import ru.kyamshanov.mission.components.project.api.common.TaskId
import ru.kyamshanov.mission.components.project.api.editing.di.EditProjectComponent
import ru.kyamshanov.mission.components.project.impl.edit.di.EditProjectModuleComponent
import ru.kyamshanov.mission.components.project.impl.edit.domain.model.TaskPointsEditingScheme
import ru.kyamshanov.mission.components.project.impl.edit.domain.model.TaskPointsInfo
import ru.kyamshanov.mission.core.navigation.api.di.NavigationComponent
import ru.kyamshanov.mission.core.navigation.common.utils.di
import ru.kyamshanov.mission.core.navigation.common.utils.diInternal
import ru.kyamshanov.mission.core.navigation.common.utils.getValue
import ru.kyamshanov.mission.components.project.impl.edit.ui.model.TotalPointsInfo
import ru.kyamshanov.mission.project_view.impl.ui.model.TotalPointsScreenState


internal interface TotalPointsViewModel {

    val screenState: Value<TotalPointsScreenState>
    fun setPoints(taskId: TaskId, points: Int?)
    fun clickOnBack()
    fun saveChanges()
}

internal class TotalPointsUiComponent(
    private val projectName: String,
    private val sourceTaskPoints: List<TaskPointsInfo>,
    private val context: ComponentContext
) : ComponentContext by context {

    private val internalApi by requireNotNull(instanceKeeper.diInternal<EditProjectComponent, EditProjectModuleComponent>())
    private val navigationComponent by requireNotNull(instanceKeeper.di<NavigationComponent>())

    val viewModel: TotalPointsViewModel =
        instanceKeeper.getOrCreate(::TotalPointsRetainedInstance)

    private val pointsInteractor get() = internalApi.pointsInteractor
    private val navigator get() = navigationComponent.navigator

    private inner class TotalPointsRetainedInstance : InstanceKeeper.Instance,
        TotalPointsViewModel {

        private val viewModelScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

        private val _screenState = MutableValue(
            TotalPointsScreenState(TotalPointsInfo(projectName, sourceTaskPoints), TaskPointsEditingScheme())
        )
        override val screenState = _screenState

        init {
            pointsInteractor.loadTasks(sourceTaskPoints)
            _screenState.update { it.copy(editingScheme = pointsInteractor.editableScheme) }
        }

        override fun setPoints(taskId: TaskId, points: Int?) {
            pointsInteractor.setPoints(taskId, points)
                .onSuccess { stagePoints ->
                    _screenState.update {
                        it.copy(totalPointsInfo = it.totalPointsInfo.copy(stagePoints = stagePoints))
                    }
                }
        }

        override fun saveChanges() {
            viewModelScope.launch {
                pointsInteractor.saveChanges()
                    .onSuccess { editingScheme ->
                        _screenState.update { it.copy(editingScheme = editingScheme) }
                    }
                    .onFailure {
                        println("Пошёл на ***")
                    }
            }
        }

        override fun clickOnBack() {
            navigator.exit()
        }

        override fun onDestroy() {
            viewModelScope.cancel()
        }
    }
}