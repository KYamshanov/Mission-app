package ru.kyamshanov.mission.foundation.impl.splash_screen.di

import org.koin.dsl.module
import ru.kyamshanov.mission.core.navigation.api.Screen
import ru.kyamshanov.mission.foundation.impl.splash_screen.presentation.screens.SplashScreen

internal val splashScreenModule = module {
    scope<ModuleComponent> {
        scoped<Screen> { SplashScreen() }
    }
}