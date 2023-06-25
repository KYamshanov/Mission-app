package ru.kyamshanov.mission.core.ui.components

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@Composable
fun SwipeableRow(
    modifier: Modifier = Modifier,
    verticalAlignment: Alignment.Vertical = Alignment.Top,
    hiddenContent: @Composable BoxScope.() -> Unit,
    content: @Composable RowScope.() -> Unit,
) {
    val localDensity = LocalDensity.current
    val hiddenBoxWidthState = remember { mutableStateOf(0) }
    val hiddenBoxOffsetState = remember { mutableStateOf(0) }

    Box(modifier = modifier) {
        Row(
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset(with(localDensity) { (hiddenBoxOffsetState.value - hiddenBoxWidthState.value).toDp() })
                .scrollable(
                    orientation = Orientation.Horizontal,
                    state = rememberScrollableState { delta ->
                        val newV = hiddenBoxOffsetState.value + delta / 3
                        if (newV <= 0) return@rememberScrollableState 0f
                        if (newV >= hiddenBoxWidthState.value) return@rememberScrollableState 0f
                        hiddenBoxOffsetState.value = newV.toInt()
                        delta
                    }),
            verticalAlignment = verticalAlignment,
        ) {
            content.invoke(this)
        }

        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(with(localDensity) { hiddenBoxOffsetState.value.toDp() })
                .onSizeChanged {
                    hiddenBoxWidthState.value = it.width
                    hiddenBoxOffsetState.value = it.width
                }
                .alpha((hiddenBoxWidthState.value - hiddenBoxOffsetState.value).toFloat() / hiddenBoxWidthState.value),
        ) {
            hiddenContent.invoke(this)
        }
    }
}