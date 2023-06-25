package ru.kyamshanov.mission.core.di.impl.koin

import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class KoinComponentImpl<T : AbstractComponent>(
    private val sourceComponent: T
) : KoinComponent, AbstractComponent by sourceComponent {

    override fun getKoin() = MissionKoinContext.koin

    init {
        getKoin().loadModules(sourceComponent.modules)
    }

    init {
        getKoin().unloadModules(sourceComponent.modules)
    }

}