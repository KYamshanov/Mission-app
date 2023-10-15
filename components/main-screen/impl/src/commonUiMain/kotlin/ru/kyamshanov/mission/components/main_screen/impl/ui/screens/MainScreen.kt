package ru.kyamshanov.mission.components.main_screen.impl.ui.screens

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.parcelable.Parcelize
import ru.kyamshanov.mission.components.main_screen.impl.ui.components.MainScreenUiComponentImpl
import ru.kyamshanov.mission.components.main_screen.impl.ui.composable.MainScreenComposable
import ru.kyamshanov.mission.core.navigation.api.Screen
import ru.kyamshanov.mission.core.navigation.common.ComposableScreen


@Parcelize
internal actual class MainScreen : Screen, ComposableScreen {

    @Composable
    override fun Content(componentContext: ComponentContext) {
        val screenUiComponent = MainScreenUiComponentImpl(componentContext)

        MainScreenComposable(
            navigationBarViewModel = screenUiComponent.navigationBarViewModel,
            searchViewModel = screenUiComponent.searchViewModel,
            screenUiComponent.frontViewModel,
        )
    }
}