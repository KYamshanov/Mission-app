package ru.kyamshanov.mission.components.project.impl.edit.ui.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import ru.kyamshanov.mission.components.project.impl.edit.domain.usecase.TaskStagePresentUseCase
import ru.kyamshanov.mission.components.project.impl.edit.ui.components.ProjectViewModel
import ru.kyamshanov.mission.core.ui.components.Loader

@Composable
internal fun ProjectComposable(
    projectId: String,
    viewModel: ProjectViewModel,
    taskStagePresentUseCase: TaskStagePresentUseCase
) {

    val screenState by viewModel.screenStateFlow.subscribeAsState()
    if (screenState.loading) Loader { viewModel.onBack() }
    else screenState.projectInfo?.let { projectInfo ->
        ProjectViewComposable(
            screenState = screenState,
            projectInfo = projectInfo,
            viewModel = viewModel,
            taskStagePresentUseCase = taskStagePresentUseCase,
        )
    }
}