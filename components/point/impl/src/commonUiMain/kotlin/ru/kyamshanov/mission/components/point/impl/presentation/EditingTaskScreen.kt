package ru.kyamshanov.mission.components.point.impl.presentation

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.parcelable.Parcelize
import ru.kyamshanov.mission.core.navigation.api.Screen
import ru.kyamshanov.mission.core.navigation.common.ComposableScreen

@Parcelize
internal actual class EditingTaskScreen actual constructor(
    private val taskId: String
) : Screen, ComposableScreen {

    @Composable
    override fun Content(componentContext: ComponentContext) {
        val viewModel = EditingTaskUiComponent(taskId, componentContext).viewModel
        EditingTaskComposable(viewModel)
    }
}