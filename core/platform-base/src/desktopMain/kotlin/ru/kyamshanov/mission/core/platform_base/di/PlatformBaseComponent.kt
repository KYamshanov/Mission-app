package ru.kyamshanov.mission.core.platform_base.di

import androidx.compose.ui.awt.ComposeWindow

actual interface PlatformBaseComponent {

    val mainComposeWindow: ComposeWindow
}