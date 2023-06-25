package ru.kyamshanov.mission.core.splash_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import ru.kyamshanov.mission.MissionTheme
import ru.kyamshanov.mission.core.login_screen.impl.domain.AuthenticationLauncherImpl

@Composable
internal actual fun SplashComposable() =
    MissionTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MissionTheme.colors.background) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = "Загрузка...",
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }