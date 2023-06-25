package ru.kyamshanov.mission.core.base.impl.di

import org.koin.core.module.Module
import ru.kyamshanov.mission.core.base.api.di.BaseCoreComponent
import ru.kyamshanov.mission.core.di.impl.koin.AbstractComponentBuilder

class BaseCoreComponentBuilder : AbstractComponentBuilder<BaseCoreComponent>() {
    override val modules: List<Module> = listOf(BaseCoreModule)

    override fun build(): BaseCoreComponent = ModuleComponent()
}