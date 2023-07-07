package ru.kyamshanov.mission.components.main_screen.impl.ui.screens

import androidx.compose.runtime.Composable
import ru.kyamshanov.mission.components.main_screen.impl.ui.composable.MainScreenComponent
import ru.kyamshanov.mission.core.navigation.common.ComposableScreen

internal class MainComposableScreen : ComposableScreen {
    @Composable
    override fun Content() = MainScreenComponent()
}