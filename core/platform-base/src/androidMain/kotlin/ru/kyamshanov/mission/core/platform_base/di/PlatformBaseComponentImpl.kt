package ru.kyamshanov.mission.core.platform_base.di

import android.content.Context
import ru.kyamshanov.mission.core.di.impl.koin.AbstractComponent

internal class PlatformBaseComponentImpl(
    override val applicationContext: Context
) : AbstractComponent(), PlatformBaseComponent