package ru.kyamshanov.mission

import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Immutable
data class MissionTypography(
    val mainAppearance: TextStyle,
    val huge: TextStyle,
    val hugeMedium: TextStyle,
    val large: TextStyle,
    val medium: TextStyle,
    val smallMedium: TextStyle,
    val small: TextStyle,
    val white: TextStyle,
    val black: TextStyle,
    val blackContainer: TextStyle,
    val gray: TextStyle,
    val red: TextStyle,
    val green: TextStyle,
    val yellow: TextStyle,
    val blue: TextStyle,
    val gold: TextStyle,
) {

    val mainButtonStyle = mainAppearance + huge + white

    val secondaryButtonStyle = mainAppearance + large + black

    val alternativeButtonStyle = mainAppearance + large + blue

    val title = mainAppearance + large + black

    val field = mainAppearance + huge + blackContainer

    val titleSecondary = mainAppearance + large + blackContainer

    val inputHint = mainAppearance + smallMedium + gray
    val inputText = mainAppearance + medium + black

    val topBarTitle = mainAppearance + hugeMedium + white
    val topBarSecondaryTitle = mainAppearance + large + white
    val topBarSubtitle = mainAppearance + medium + white
}

@Composable
internal fun typographyComposable() = MissionTypography(
    mainAppearance = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
    ),
    huge = TextStyle(fontSize = 24.sp),
    hugeMedium = TextStyle(fontSize = 20.sp),
    large = TextStyle(fontSize = 18.sp),
    medium = TextStyle(fontSize = 16.sp),
    smallMedium = TextStyle(fontSize = 14.sp),
    small = TextStyle(fontSize = 12.sp),
    white = TextStyle(color = MissionTheme.colors.primary),
    black = TextStyle(color = MissionTheme.colors.darkSecondary),
    gray = TextStyle(color = MissionTheme.colors.gray),
    blackContainer = TextStyle(color = MissionTheme.colors.darkPrimary),
    red = TextStyle(color = MissionTheme.colors.wrong),
    green = TextStyle(color = MissionTheme.colors.success),
    yellow = TextStyle(color = YellowPleasant),
    blue = TextStyle(color = MissionTheme.colors.secondary),
    gold = TextStyle(color = MissionTheme.colors.gold),
)

internal val LocalExtendedTypography = staticCompositionLocalOf {
    MissionTypography(
        large = TextStyle.Default,
        huge = TextStyle.Default,
        medium = TextStyle.Default,
        smallMedium = TextStyle.Default,
        small = TextStyle.Default,
        mainAppearance = TextStyle.Default,
        white = TextStyle.Default,
        black = TextStyle.Default,
        gray = TextStyle.Default,
        blackContainer = TextStyle.Default,
        red = TextStyle.Default,
        green = TextStyle.Default,
        yellow = TextStyle.Default,
        blue = TextStyle.Default,
        hugeMedium = TextStyle.Default,
        gold = TextStyle.Default,
    )
}

internal val MissionTextSelectionColors = TextSelectionColors(
    handleColor = BluePleasant,
    backgroundColor = BluePleasant.copy(alpha = 0.4f)
)