package ru.kyamshanov.mission.components.project.impl.edit.ui.screen

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.parcelable.Parcelize
import ru.kyamshanov.mission.components.project.impl.edit.ui.components.ParticipantsListUiComponent
import ru.kyamshanov.mission.components.project.impl.edit.ui.composable.ParticipantsListComposable
import ru.kyamshanov.mission.components.project.impl.edit.ui.model.ProjectInfoSlim
import ru.kyamshanov.mission.core.navigation.api.Screen
import ru.kyamshanov.mission.core.navigation.common.ComposableScreen

@Parcelize
internal actual class ParticipantsListScreen actual constructor(private val projectInfoSlim: ProjectInfoSlim) :
    Screen, ComposableScreen {

    @Composable
    override fun Content(componentContext: ComponentContext) {
        val viewModel = ParticipantsListUiComponent(projectInfoSlim, componentContext).viewModel
        ParticipantsListComposable(projectInfoSlim, viewModel)
    }

}