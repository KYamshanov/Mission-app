package ru.kyamshanov.mission.core.splash_screen

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen

@Composable
internal expect fun SplashComposable()

internal class SplashScreen : Screen {

    @Composable
    override fun Content() {

        SplashComposable()
    }

}


