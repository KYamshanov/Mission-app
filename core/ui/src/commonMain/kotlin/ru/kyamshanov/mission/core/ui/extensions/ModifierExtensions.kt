package ru.kyamshanov.mission.core.ui.extensions

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewConfiguration

expect fun Modifier.imePadding(): Modifier

expect fun Modifier.navigationBarsPadding(): Modifier

expect fun Modifier.systemBarsPadding(): Modifier

@Composable
expect fun ViewConfiguration.getOrientation(): Int

@Composable
expect fun WindowInsets.Companion.getStatusBars(): WindowInsets