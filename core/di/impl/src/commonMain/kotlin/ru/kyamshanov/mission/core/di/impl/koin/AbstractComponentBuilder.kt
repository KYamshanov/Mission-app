package ru.kyamshanov.mission.core.di.impl.koin

import org.koin.core.module.Module
import ru.kyamshanov.mission.core.di.api.ComponentBuilder

abstract class AbstractComponentBuilder<T : Any> : ComponentBuilder<T> {

    abstract val modules: List<Module>

}