package ru.kyamshanov.mission.core.ui.extensions

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.ViewConfiguration

actual fun Modifier.imePadding(): Modifier = imePadding()
actual fun Modifier.navigationBarsPadding(): Modifier = navigationBarsPadding()

@Composable
actual fun ViewConfiguration.getOrientation(): Int = LocalConfiguration.current.orientation

@Composable
actual fun WindowInsets.Companion.getStatusBars(): WindowInsets = WindowInsets.statusBars
actual fun Modifier.systemBarsPadding(): Modifier = systemBarsPadding()