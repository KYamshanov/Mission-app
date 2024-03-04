package ru.kyamshanov.mission.components.point.impl.presentation.screens

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.parcelable.Parcelize
import ru.kyamshanov.mission.components.point.impl.presentation.EditingProjectComposable
import ru.kyamshanov.mission.components.point.impl.presentation.EditingProjectUiComponent
import ru.kyamshanov.mission.components.points.api.di.TaskComponent
import ru.kyamshanov.mission.core.navigation.api.Screen
import ru.kyamshanov.mission.core.navigation.api.di.NavigationComponent
import ru.kyamshanov.mission.core.navigation.common.ComposableScreen
import ru.kyamshanov.mission.core.navigation.common.utils.buildComponent

@Parcelize
internal actual class EditingProjectScreen actual constructor(
    private val projectId: String
) : Screen, ComposableScreen {

    @Composable
    override fun Content(componentContext: ComponentContext) {
        val viewModel =
            componentContext.buildComponent(NavigationComponent::class, TaskComponent::class) {
                EditingProjectUiComponent(
                    projectId = projectId,
                    context = it,
                    navigator = get(),
                    interactor = get(),
                    taskInteractor = get(),
                    searchInteractor = get(),
                    taskLauncher = get()
                )
            }
        EditingProjectComposable(viewModel)
    }
}