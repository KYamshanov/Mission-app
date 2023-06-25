package ru.kyamshanov.mission.core.di.impl.koin

import ru.kyamshanov.mission.core.di.api.ComponentBuilder

class KoinComponentBuilder<T : AbstractComponent>(
    private val sourceAbstractComponentBuilder: AbstractComponentBuilder<T>
) : ComponentBuilder<T> {

    override fun build(): T {
        val koin = MissionKoinContext.koin
        koin.loadModules(sourceAbstractComponentBuilder.modules)
        val component = sourceAbstractComponentBuilder.build()
       // koin.unloadModules(sourceAbstractComponentBuilder.modules)
        return component
    }
}