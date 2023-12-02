package ru.kyamshanov.mission.components.point.impl.presentation.screens

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.parcelable.Parcelize
import ru.kyamshanov.mission.components.point.impl.presentation.CreationProjectComposable
import ru.kyamshanov.mission.components.point.impl.presentation.CreationProjectUiComponent
import ru.kyamshanov.mission.components.point.impl.presentation.CreationTaskComposable
import ru.kyamshanov.mission.components.point.impl.presentation.CreationTaskUiComponent
import ru.kyamshanov.mission.core.navigation.api.Screen
import ru.kyamshanov.mission.core.navigation.common.ComposableScreen

@Parcelize
internal actual class CreationProjectScreen actual constructor() : Screen, ComposableScreen {

    @Composable
    override fun Content(componentContext: ComponentContext) {
        val viewModel = CreationProjectUiComponent(componentContext).viewModel
        CreationProjectComposable(viewModel)
    }
}