package ru.kyamshanov.mission.foundation.impl.splash_screen.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay
import ru.kyamshanov.mission.core.di.impl.Di
import ru.kyamshanov.mission.core.navigation.common.ComposableScreen
import ru.kyamshanov.mission.foundation.api.login.di.AuthenticationComponent
import ru.kyamshanov.mission.foundation.impl.splash_screen.presentation.composables.SplashComposable


internal class SplashScreen : ComposableScreen {

    @Composable
    override fun Content() {
        SplashComposable()
        LaunchedEffect(true) {
            delay(1000)
            requireNotNull(Di.getComponent<AuthenticationComponent>()).launcher.launch()
        }
    }
}


