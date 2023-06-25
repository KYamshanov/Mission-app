package ru.kyamshanov.mission.core.navigation.impl

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.CurrentScreen
import ru.kyamshanov.mission.core.di.impl.Di
import ru.kyamshanov.mission.core.navigation.api.di.NavigationComponent
import ru.kyamshanov.mission.core.navigation.common.ComposableScreen
import ru.kyamshanov.mission.core.navigation.impl.di.ModuleComponent
import cafe.adriel.voyager.navigator.Navigator as VNavigator

object NavigationLauncher {

    @Composable
    fun launch(splashScreen: ComposableScreen) = VNavigator(splashScreen) {
        CurrentScreen()
        Di.getInternalComponent<NavigationComponent, ModuleComponent>().navigatorControllerHolder.navigator = it
    }
}