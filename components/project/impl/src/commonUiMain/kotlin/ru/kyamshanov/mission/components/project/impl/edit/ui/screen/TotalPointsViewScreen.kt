package ru.kyamshanov.mission.components.project.impl.edit.ui.screen

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.parcelable.Parcelize
import ru.kyamshanov.mission.components.project.impl.edit.ui.components.TotalPointsUiComponent
import ru.kyamshanov.mission.components.project.impl.edit.ui.composable.TotalPointsView
import ru.kyamshanov.mission.components.project.impl.edit.ui.model.TotalPointsInfo
import ru.kyamshanov.mission.core.navigation.api.Screen
import ru.kyamshanov.mission.core.navigation.common.ComposableScreen

@Parcelize
internal actual class TotalPointsViewScreen actual constructor(private val points: TotalPointsInfo) :
    Screen, ComposableScreen {
    @Composable
    override fun Content(componentContext: ComponentContext) {
        val viewModel =
            TotalPointsUiComponent(
                projectName = points.projectName,
                sourceTaskPoints = points.stagePoints,
                context = componentContext
            ).viewModel

        TotalPointsView(points, viewModel)
    }
}