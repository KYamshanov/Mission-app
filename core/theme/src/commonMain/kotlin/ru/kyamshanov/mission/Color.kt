package ru.kyamshanov.mission

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class MissionColors(
    val background: Color,
    val baseButtonText: Color,
    val input: Color,
    val secondButton: Color,
    val secondButtonText: Color,
    val success: Color,
    val wrong: Color,
    val focusedBorder: Color,
    val darkSecondary: Color,
    val darkPrimary: Color,
    val border: Color,
    val gray: Color,
    val primary: Color,
    val primaryContainer: Color,
    val secondary: Color,
    val gold: Color,
)

internal val White = Color(0xFFFFFFFF)
internal val Black = Color(0xFF000000)
internal val Purple200 = Color(0xFFBB86FC)
internal val Purple500 = Color(0xFF6200EE)
internal val Purple700 = Color(0xFF3700B3)
internal val Teal200 = Color(0xFF03DAC5)
internal val RedPleasant = Color(0xFFFD3223)
internal val WhitePleasant = Color(0xFFF2F4FB)
internal val BluePleasant = Color(0xFF164ACF)
internal val BlackPleasant = Color(0xFF3C3C3C)
internal val GrayPleasant = Color(0xFFD9D9D9)
internal val GreenPleasant = Color(0xFF17830E)
internal val YellowPleasant = Color(0xFFF1EA3E)
internal val OrangePleasant = Color(0xFFF2994A)

internal val DarkColorPalette = MissionColors(
    background = GrayPleasant,
    secondary = Color.Blue,
    baseButtonText = White,
    input = White,
    darkSecondary = Black,
    darkPrimary = BlackPleasant,
    secondButton = GrayPleasant,
    secondButtonText = Black,
    success = GreenPleasant,
    wrong = RedPleasant,
    focusedBorder = Black,
    border = BlackPleasant,
    gray = GrayPleasant,
    primary = White,
    primaryContainer = WhitePleasant,
    gold = OrangePleasant,
)

internal val LightColorPalette = MissionColors(
    background = WhitePleasant,
    secondary = BluePleasant,
    baseButtonText = White,
    input = White,
    darkSecondary = Black,
    darkPrimary = BlackPleasant,
    secondButton = GrayPleasant,
    secondButtonText = Black,
    success = GreenPleasant,
    wrong = RedPleasant,
    focusedBorder = Black,
    border = BlackPleasant,
    gray = GrayPleasant,
    primary = White,
    primaryContainer = WhitePleasant,
    gold = OrangePleasant,
)

internal val LocalExtendedColors = staticCompositionLocalOf {
    MissionColors(
        background = Color.Unspecified,
        secondary = Color.Unspecified,
        baseButtonText = Color.Unspecified,
        input = Color.Unspecified,
        darkSecondary = Color.Unspecified,
        darkPrimary = Color.Unspecified,
        secondButton = Color.Unspecified,
        secondButtonText = Color.Unspecified,
        success = Color.Unspecified,
        wrong = Color.Unspecified,
        focusedBorder = Color.Unspecified,
        border = Color.Unspecified,
        gray = Color.Unspecified,
        primary = Color.Unspecified,
        primaryContainer = Color.Unspecified,
        gold = Color.Unspecified,
    )
}

