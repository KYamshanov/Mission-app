package ru.kyamshanov.mission.core.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntSize
import ru.kyamshanov.mission.MissionTheme

@Composable
fun TextField(
    modifier: Modifier = Modifier,
    text: String,
    label: String? = null,
    rightIcon: (@Composable () -> Unit)? = null,
    underlined: Boolean = false,
) = TextField(
    modifier = modifier,
    label = label,
    content = { Text(text = text, style = MissionTheme.typography.inputText) },
    underlined = underlined,
    rightIcon = rightIcon,
)

@Composable
fun TextField(
    modifier: Modifier = Modifier,
    label: String? = null,
    underlined: Boolean = false,
    rightIcon: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit,
) = BoxWithConstraints(modifier = modifier) {
    val localDensity = LocalDensity.current
    val iconSizeState = remember { mutableStateOf(IntSize.Zero) }


    Column(
        modifier = Modifier.align(Alignment.TopStart)
            .width(this.maxWidth - with(localDensity) { iconSizeState.value.width.toDp() })
    ) {
        label?.let { Text(it, style = MissionTheme.typography.inputHint) }
        content.invoke()
    }

    rightIcon?.let {
        Box(
            modifier = Modifier.align(Alignment.CenterEnd).onSizeChanged { iconSizeState.value = it }) { it.invoke() }
    }
    if (underlined) CellLine(modifier = Modifier.align(Alignment.BottomStart))
}