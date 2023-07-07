package ru.kyamshanov.mission.components.main_screen.impl.di

import org.koin.dsl.module
import ru.kyamshanov.mission.components.main_screen.api.domain.MainScreenLauncher
import ru.kyamshanov.mission.components.main_screen.impl.ui.navigation.MainScreenLauncherImpl

internal val mainScreenModule = module {
    scope<ModuleComponent> {
        scoped<MainScreenLauncher> { MainScreenLauncherImpl() }
    }
}