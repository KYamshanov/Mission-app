package ru.kyamshanov.mission.core.navigation.common.utils

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry

@Composable
actual fun composeComponentContext(sourceComponentContext: ComponentContext?): DefaultComponentContext =
    DefaultComponentContext(lifecycle = sourceComponentContext?.lifecycle ?: LifecycleRegistry())