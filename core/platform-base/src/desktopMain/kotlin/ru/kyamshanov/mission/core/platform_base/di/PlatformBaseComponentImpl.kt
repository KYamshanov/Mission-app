package ru.kyamshanov.mission.core.platform_base.di

import androidx.compose.ui.awt.ComposeWindow
import ru.kyamshanov.mission.core.di.impl.koin.AbstractComponent

internal class PlatformBaseComponentImpl(
    override val mainComposeWindow: ComposeWindow
) : AbstractComponent(), PlatformBaseComponent