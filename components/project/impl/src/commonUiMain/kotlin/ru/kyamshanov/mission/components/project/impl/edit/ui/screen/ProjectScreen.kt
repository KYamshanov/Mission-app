package ru.kyamshanov.mission.components.project.impl.edit.ui.screen

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.parcelable.Parcelize
import ru.kyamshanov.mission.components.project.impl.edit.ui.components.ProjectUiComponent
import ru.kyamshanov.mission.components.project.impl.edit.ui.composable.ProjectComposable
import ru.kyamshanov.mission.core.navigation.api.Screen
import ru.kyamshanov.mission.core.navigation.common.ComposableScreen

@Parcelize
internal actual class ProjectScreen actual constructor(private val projectId: String) : Screen,
    ComposableScreen {

    @Composable
    override fun Content(componentContext: ComponentContext) {
        val uiComponent = ProjectUiComponent(projectId, componentContext)
        ProjectComposable(projectId, uiComponent.viewModel, uiComponent.taskStagePresentUseCase)
    }

}