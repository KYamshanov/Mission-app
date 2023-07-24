package ru.kyamshanov.mission.core.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import ru.kyamshanov.mission.MissionTheme

@Composable
fun Loader(
    onDismissRequest: () -> Unit,
) =
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            modifier = Modifier.drawBehind {
                drawCircle(
                    Color.Transparent,
                    radius = size.width,
                )
            },
            color = MissionTheme.colors.gray,
        )
    }