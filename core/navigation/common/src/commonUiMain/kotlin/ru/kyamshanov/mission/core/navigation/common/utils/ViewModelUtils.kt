package ru.kyamshanov.mission.core.navigation.common.utils

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DefaultComponentContext

@Composable
expect fun composeComponentContext(sourceComponentContext: ComponentContext? = null): DefaultComponentContext