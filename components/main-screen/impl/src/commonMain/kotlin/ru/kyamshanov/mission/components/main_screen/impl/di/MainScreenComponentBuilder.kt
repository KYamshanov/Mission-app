package ru.kyamshanov.mission.components.main_screen.impl.di

import org.koin.core.module.Module
import ru.kyamshanov.mission.components.main_screen.api.di.MainScreenComponent
import ru.kyamshanov.mission.core.di.impl.koin.AbstractComponentBuilder

class MainScreenComponentBuilder : AbstractComponentBuilder<MainScreenComponent>() {

    override val modules: List<Module> = listOf(mainScreenModule)

    override fun build(): MainScreenComponent = ModuleComponent()
}