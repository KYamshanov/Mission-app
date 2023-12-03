package ru.kyamshanov.mission.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import ru.kyamshanov.mission.MissionTheme

@Composable
fun LabelButton(
    modifier: Modifier = Modifier,
    label: String,
    color: Long,
    onClick: (() -> Unit)? = null,
) {
    val interactionSource = remember { MutableInteractionSource() }
    Text(
        modifier = modifier
            .run {
                if (onClick != null) {
                    clickable(
                        interactionSource = interactionSource,
                        indication = null,
                    ) { onClick.invoke() }
                } else this
            }
            .background(Color(color), RoundedCornerShape(50))
            .padding(horizontal = 5.dp, vertical = 2.dp),
        text = label,
        style = MissionTheme.typography.labelButtonStyle
    )
}