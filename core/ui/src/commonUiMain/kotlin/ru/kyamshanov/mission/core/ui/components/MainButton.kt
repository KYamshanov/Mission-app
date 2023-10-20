package ru.kyamshanov.mission.core.ui.components

import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.kyamshanov.mission.MissionTheme

@Composable
fun MainButton(
    modifier: Modifier = Modifier,
    label: String,
    isSoftStyle: Boolean = false,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        content = {
            Text(
                text = label,
                style = MissionTheme.typography.mainButtonStyle
            )
        },
        colors = buttonColors(
            backgroundColor = if (isSoftStyle) MissionTheme.colors.softSecondary else MissionTheme.colors.secondary
        ),
        shape = MissionTheme.shapes.mediumLarge
    )
}