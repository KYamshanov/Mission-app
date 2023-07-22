package ru.kyamshanov.mission

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

object MissionTheme {

    val colors: MissionColors
        @Composable
        get() = LocalExtendedColors.current

    val typography: MissionTypography
        @Composable
        get() = LocalExtendedTypography.current

    val shapes: MissionShapes
        @Composable
        get() = LocalExtendedShape.current
}

@Composable
fun MissionTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) DarkColorPalette else LightColorPalette

    CompositionLocalProvider(
        LocalExtendedColors provides colors,
        LocalExtendedShape provides Shapes,
        LocalTextSelectionColors provides MissionTextSelectionColors,
    ) {
        ProvideTypography(content)
    }
}

@Composable
private fun ProvideTypography(context: @Composable () -> Unit) {
    CompositionLocalProvider(LocalExtendedTypography provides typographyComposable()) {
        context.invoke()
    }
}