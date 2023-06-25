package ru.kyamshanov.mission.core.di.impl.koin

import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.component.inject
import org.koin.core.module.Module

abstract class AbstractComponent : KoinComponent {

    override fun getKoin() = MissionKoinContext.koin

    inline fun <reified T : Any> AbstractComponent.resolve(): T = get<T>()
}

