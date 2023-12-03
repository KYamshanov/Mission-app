package ru.kyamshanov.mission.core.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.kyamshanov.mission.MissionTheme

@Composable
fun Search(
    modifier: Modifier = Modifier,
    value: String,
    trailingIcon: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    onValueChange: (String) -> Unit,
) {
    TextField(
        modifier = modifier
            .fillMaxWidth()
            .border(
                color = MissionTheme.colors.secondary,
                width = 3.dp,
                shape = MissionTheme.shapes.mediumHuge
            ),
        value = value,
        onValueChange = onValueChange,
        textStyle = MissionTheme.typography.large,
        shape = MissionTheme.shapes.mediumHuge,
        trailingIcon = trailingIcon,
        leadingIcon = leadingIcon,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MissionTheme.colors.input,
            cursorColor = MissionTheme.colors.darkSecondary,
            focusedIndicatorColor = Color.Unspecified,
            unfocusedIndicatorColor = Color.Unspecified,
            disabledIndicatorColor = Color.Unspecified,
        ),
        maxLines = 1
    )
}