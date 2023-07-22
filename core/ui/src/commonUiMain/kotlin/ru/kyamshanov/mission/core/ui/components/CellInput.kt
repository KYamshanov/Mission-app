package ru.kyamshanov.mission.core.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.kyamshanov.mission.MissionTheme

@Composable
fun CellInput(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    maxLines: Int = 1,
    editable: Boolean = true,
    isMasked: Boolean = false,
) = Cell {
    MissionTextField(
        modifier = modifier
            .fillMaxWidth(),
        text = value,
        onValueChange = onValueChange,
        textStyle = MissionTheme.typography.large,
        label = { Text(text = label, style = MissionTheme.typography.inputHint) },
        maxLines = maxLines,
        editable = editable,
        underlined = false,
        isMasked = isMasked,
    )
}