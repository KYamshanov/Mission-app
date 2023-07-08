package ru.kyamshanov.mission.core.ui.extensions

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewConfiguration

actual fun Modifier.imePadding(): Modifier = this
actual fun Modifier.navigationBarsPadding(): Modifier = this
actual fun ViewConfiguration.getOrientation(): Int = 0
actual fun WindowInsets.Companion.getStatusBars(): WindowInsets = WindowInsets(0, 0, 0, 0)
actual fun Modifier.systemBarsPadding(): Modifier = this

@Composable
actual fun isSingleLineSupported(): Boolean = false