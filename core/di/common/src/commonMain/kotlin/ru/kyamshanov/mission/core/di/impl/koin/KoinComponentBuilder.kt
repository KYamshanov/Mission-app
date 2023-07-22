package ru.kyamshanov.mission.core.di.impl.koin

import ru.kyamshanov.mission.core.di.api.ComponentBuilder

class KoinComponentBuilder<T : AbstractComponent>(
    private val sourceAbstractComponentBuilder: AbstractComponentBuilder<T>
) : ComponentBuilder<T> {

    init {
        val koin = MissionKoinContext.koin
        koin.loadModules(sourceAbstractComponentBuilder.modules)
    }

    override fun build(): T = sourceAbstractComponentBuilder.build()
}