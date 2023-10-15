package ru.kyamshanov.mission.core.platform_base.di

import androidx.compose.ui.awt.ComposeWindow
import org.koin.core.module.Module
import ru.kyamshanov.mission.core.di.impl.koin.AbstractComponentBuilder

actual class PlatformBaseComponentBuilder(
    private val mainComposeWindow: ComposeWindow
) : AbstractComponentBuilder<PlatformBaseComponent>() {
    override fun build(): PlatformBaseComponent = PlatformBaseComponentImpl(mainComposeWindow)

    override val modules: List<Module> = emptyList()
}