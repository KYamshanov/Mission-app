package ru.kyamshanov.mission.core.navigation.impl.di

import org.koin.core.module.Module
import ru.kyamshanov.mission.core.di.api.ComponentBuilder
import ru.kyamshanov.mission.core.di.impl.koin.AbstractComponent
import ru.kyamshanov.mission.core.di.impl.koin.AbstractComponentBuilder
import ru.kyamshanov.mission.core.navigation.api.di.NavigationComponent

class NavigationComponentBuilder : AbstractComponentBuilder<NavigationComponent>() {

    override val modules: List<Module> = listOf(bindsModule)
    override fun build(): NavigationComponent = ModuleComponent()

}