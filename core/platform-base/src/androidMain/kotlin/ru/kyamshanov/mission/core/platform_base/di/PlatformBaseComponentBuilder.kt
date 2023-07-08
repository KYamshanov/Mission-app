package ru.kyamshanov.mission.core.platform_base.di

import android.content.Context
import org.koin.core.module.Module
import ru.kyamshanov.mission.core.di.impl.koin.AbstractComponentBuilder

actual class PlatformBaseComponentBuilder(
    private val applicationContext: Context
) : AbstractComponentBuilder<PlatformBaseComponent>() {
    override val modules: List<Module> = emptyList()

    override fun build(): PlatformBaseComponent = PlatformBaseComponentImpl(applicationContext)

}