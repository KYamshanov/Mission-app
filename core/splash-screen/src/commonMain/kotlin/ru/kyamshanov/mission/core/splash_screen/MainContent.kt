package ru.kyamshanov.mission.core.splash_screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import kotlinx.coroutines.delay
import ru.kyamshanov.mission.core.login_screen.impl.domain.AuthenticationLauncherImpl

@Composable
fun MainContent() = Navigator(SplashScreen()) {
    CurrentScreen()
    AuthenticationLauncherImpl.nav = it

    LaunchedEffect(true) {
        delay(5000)

        AuthenticationLauncherImpl.launch()
    }
}


