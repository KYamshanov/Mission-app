package ru.kyamshanov.mission.foundation.impl.splash_screen.di

import ru.kyamshanov.mission.core.di.impl.koin.AbstractComponent
import ru.kyamshanov.mission.core.navigation.common.ComposableScreen
import ru.kyamshanov.mission.foundation.api.splash_screen.di.SplashScreenComponent

internal class ModuleComponent : AbstractComponent(), SplashScreenComponent {

    override val composableSplashScreen: ComposableScreen = resolve()
}