package ru.kyamshanov.mission.components.main_screen.impl.ui.navigation

import ru.kyamshanov.mission.components.main_screen.api.domain.MainScreenLauncher
import ru.kyamshanov.mission.components.main_screen.impl.ui.screens.MainComposableScreen
import ru.kyamshanov.mission.core.di.impl.Di
import ru.kyamshanov.mission.core.navigation.api.di.NavigationComponent

internal class MainScreenLauncherImpl : MainScreenLauncher {

    override fun launch() {
        requireNotNull(Di.getComponent<NavigationComponent>()).navigator.navigateTo(MainComposableScreen())
    }
}