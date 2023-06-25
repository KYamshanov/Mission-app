package ru.kyamshanov.mission.core.navigation.impl

import cafe.adriel.voyager.navigator.Navigator as VNavigator
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.CurrentScreen
import ru.kyamshanov.mission.MissionTheme
import ru.kyamshanov.mission.core.di.impl.Di
import ru.kyamshanov.mission.core.navigation.api.di.NavigationComponent
import ru.kyamshanov.mission.core.navigation.common.ComposableScreen
import ru.kyamshanov.mission.core.navigation.impl.di.ModuleComponent

object NavigationLauncher {

    @Composable
    fun launch(splashScreen: ComposableScreen) = VNavigator(splashScreen) {
        MissionTheme {
            Surface(modifier = Modifier.fillMaxSize(), color = MissionTheme.colors.background) {
                CurrentScreen()
            }
        }
        Di.getInternalComponent<NavigationComponent, ModuleComponent>().navigatorControllerHolder.navigator = it
    }
}