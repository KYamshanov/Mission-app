package ru.kyamshanov.mission.foundation.impl.splash_screen.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import ru.kyamshanov.mission.MissionTheme
import ru.kyamshanov.mission.core.ui.Res

@Composable
internal actual fun SplashComposable() =
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier.width(108.dp).height(108.dp),
            painter = painterResource(Res.images.app_icon),
            contentDescription = stringResource(Res.strings.app_name),
        )
        Text(
            text = stringResource(Res.strings.app_name),
            style = MissionTheme.typography.secondaryButtonStyle,
            modifier = Modifier.padding(16.dp)
        )
    }