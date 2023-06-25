package ru.kyamshanov.mission.core.splash_screen

import androidx.compose.runtime.Composable
import ru.kyamshanov.mission.core.navigation.impl.NavigationLauncher

@Composable
fun MainContent() = NavigationLauncher.launch(SplashScreen())


