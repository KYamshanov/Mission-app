package ru.kyamshanov.mission.components.main_screen.impl.di

import ru.kyamshanov.mission.components.main_screen.api.di.MainScreenComponent
import ru.kyamshanov.mission.components.main_screen.impl.ui.screens.MainScreen
import ru.kyamshanov.mission.core.di.impl.koin.AbstractComponent
import ru.kyamshanov.mission.core.navigation.api.Screen

internal class ModuleComponent : AbstractComponent(), MainScreenComponent {

    override val entryScreen: Screen get() = MainScreen()
}