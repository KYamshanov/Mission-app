package ru.kyamshanov.mission.core.platform_base.di

import org.koin.core.module.Module
import ru.kyamshanov.mission.core.di.impl.koin.AbstractComponentBuilder

actual class PlatformBaseComponentBuilder : AbstractComponentBuilder<PlatformBaseComponent>() {
    override fun build(): PlatformBaseComponent = PlatformBaseComponentImpl()

    override val modules: List<Module> = emptyList()
}