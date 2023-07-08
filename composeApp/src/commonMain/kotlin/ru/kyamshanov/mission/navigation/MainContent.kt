package ru.kyamshanov.mission.navigation

import androidx.compose.runtime.Composable
import ru.kyamshanov.mission.core.di.impl.Di
import ru.kyamshanov.mission.core.navigation.impl.NavigationLauncher
import ru.kyamshanov.mission.foundation.api.splash_screen.di.SplashScreenComponent

@Composable
fun MainContent() {
    val splashScreenComponent = requireNotNull(Di.getComponent<SplashScreenComponent>())
    NavigationLauncher.launch(splashScreenComponent.composableSplashScreen)
}


