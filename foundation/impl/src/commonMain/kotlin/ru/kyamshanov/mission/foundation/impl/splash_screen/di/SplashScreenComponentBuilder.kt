package ru.kyamshanov.mission.foundation.impl.splash_screen.di

import org.koin.core.module.Module
import ru.kyamshanov.mission.core.di.impl.koin.AbstractComponentBuilder
import ru.kyamshanov.mission.core.navigation.api.di.NavigationComponent
import ru.kyamshanov.mission.foundation.api.splash_screen.di.SplashScreenComponent

class SplashScreenComponentBuilder : AbstractComponentBuilder<SplashScreenComponent>() {

    override val modules: List<Module> = listOf(splashScreenModule)
    override fun build(): SplashScreenComponent = ModuleComponent()

}