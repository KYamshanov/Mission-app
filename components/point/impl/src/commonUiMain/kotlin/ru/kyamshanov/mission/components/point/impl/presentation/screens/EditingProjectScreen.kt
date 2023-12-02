package ru.kyamshanov.mission.components.point.impl.presentation.screens

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.parcelable.Parcelize
import ru.kyamshanov.mission.components.point.impl.presentation.EditingProjectComposable
import ru.kyamshanov.mission.components.point.impl.presentation.EditingProjectUiComponent
import ru.kyamshanov.mission.components.point.impl.presentation.EditingTaskComposable
import ru.kyamshanov.mission.components.point.impl.presentation.EditingTaskUiComponent
import ru.kyamshanov.mission.core.navigation.api.Screen
import ru.kyamshanov.mission.core.navigation.common.ComposableScreen

@Parcelize
internal actual class EditingProjectScreen actual constructor(
    private val projectId: String
) : Screen, ComposableScreen {

    @Composable
    override fun Content(componentContext: ComponentContext) {
        val viewModel = EditingProjectUiComponent(projectId, componentContext).viewModel
        EditingProjectComposable(viewModel)
    }
}