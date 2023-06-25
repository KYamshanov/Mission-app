package ru.kyamshanov.mission.core.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.kyamshanov.mission.MissionTheme

@Composable
fun Input(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    maxLines: Int = 1,
    rightIcon: (@Composable () -> Unit)? = null,
) {
    MissionTextField(
        modifier = modifier
            .fillMaxWidth(),
        text = value,
        onValueChange = onValueChange,
        textStyle = MissionTheme.typography.inputText,
        label = { Text(text = label, style = MissionTheme.typography.inputHint) },
        maxLines = maxLines,
        rightIcon = rightIcon
    )
}