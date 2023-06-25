package ru.kyamshanov.mission.core.di.impl.koin

import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.module.Module

interface AbstractComponent : KoinComponent {
    override fun getKoin() = MissionKoinContext.koin

    abstract val modules: List<Module>

}

inline fun <reified T : Any> AbstractComponent.provide(): T {
    return get<T>()
}