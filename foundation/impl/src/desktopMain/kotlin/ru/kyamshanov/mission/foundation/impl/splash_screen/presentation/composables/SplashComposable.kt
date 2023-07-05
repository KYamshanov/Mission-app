package ru.kyamshanov.mission.foundation.impl.splash_screen.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.kyamshanov.mission.MissionTheme

@Composable
internal actual fun SplashComposable() =
    Box(contentAlignment = Alignment.Center, modifier = Modifier.background(MissionTheme.colors.background)) {
        Text(
            text = "Загрузка...",
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(16.dp)
        )
    }
