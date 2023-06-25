package ru.kyamshanov.mission.core.splash_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import ru.kyamshanov.mission.MissionTheme
import ru.kyamshanov.mission.core.ui.R

@Composable
internal actual fun SplashComposable() = MissionTheme {
    Surface(modifier = Modifier.fillMaxSize(), color = MissionTheme.colors.background) {
        Box(contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(id = R.drawable.app_icon),
                contentDescription = "Удалить",
            )
        }
    }
}