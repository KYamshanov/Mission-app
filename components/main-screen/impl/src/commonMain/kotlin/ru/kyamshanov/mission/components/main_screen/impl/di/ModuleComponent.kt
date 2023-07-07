package ru.kyamshanov.mission.components.main_screen.impl.di

import ru.kyamshanov.mission.components.main_screen.api.di.MainScreenComponent
import ru.kyamshanov.mission.components.main_screen.api.domain.MainScreenLauncher
import ru.kyamshanov.mission.core.di.impl.koin.AbstractComponent

internal class ModuleComponent : AbstractComponent(), MainScreenComponent {


    override val launcher: MainScreenLauncher = resolve()
}