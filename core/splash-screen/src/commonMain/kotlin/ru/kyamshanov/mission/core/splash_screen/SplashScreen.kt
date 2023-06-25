package ru.kyamshanov.mission.core.splash_screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay
import ru.kyamshanov.mission.core.di.impl.Di
import ru.kyamshanov.mission.core.login_screen.api.di.AuthenticationComponent
import ru.kyamshanov.mission.core.navigation.common.ComposableScreen

@Composable
internal expect fun SplashComposable()

internal class SplashScreen : ComposableScreen {

    @Composable
    override fun Content() {
        SplashComposable()
        LaunchedEffect(true) {
            delay(5000)
            requireNotNull(Di.getComponent<AuthenticationComponent>()).launcher.launch()
        }
    }
}


