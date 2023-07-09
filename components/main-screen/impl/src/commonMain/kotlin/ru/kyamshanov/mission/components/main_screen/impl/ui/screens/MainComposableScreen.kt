package ru.kyamshanov.mission.components.main_screen.impl.ui.screens

import androidx.compose.runtime.Composable
import com.arkivanov.essenty.parcelable.Parcelize
import ru.kyamshanov.mission.components.main_screen.impl.ui.composable.MainScreenComponent
import ru.kyamshanov.mission.core.navigation.common.ComposableScreen

@Parcelize
internal class MainComposableScreen : ComposableScreen {
    @Composable
    override fun Content() = MainScreenComponent()
}