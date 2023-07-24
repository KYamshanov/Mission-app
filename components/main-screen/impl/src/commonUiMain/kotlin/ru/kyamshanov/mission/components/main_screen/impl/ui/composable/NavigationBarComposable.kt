package ru.kyamshanov.mission.components.main_screen.impl.ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import ru.kyamshanov.mission.MissionTheme
import ru.kyamshanov.mission.components.main_screen.impl.ui.components.NavigationBarViewModel
import ru.kyamshanov.mission.core.ui.Res

@Composable
internal fun NavigationBarComposable(
    modifier: Modifier = Modifier,
    navigationBarViewModel: NavigationBarViewModel,
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {

        NavigationHorizontalGravityItem(
            modifier = Modifier.clickable { navigationBarViewModel.openProfile() },
            imagePainter = painterResource(Res.images.ic_profile),
            description = stringResource(Res.strings.profile),
            horizontalAlignment = Alignment.CenterEnd,
        )
        if (navigationBarViewModel.hasManagerFunctionsState.value) {
            NavigationHorizontalGravityItem(
                modifier = Modifier.clickable { navigationBarViewModel.openCreatingProjectScreen() },
                imagePainter = painterResource(Res.images.ic_rounded_plus),
                description = stringResource(Res.strings.create),
                horizontalAlignment = Alignment.Center,
            )
        }
    }
}

@Composable
internal fun NavigationHorizontalGravityItem(
    modifier: Modifier,
    imagePainter: Painter,
    description: String,
    horizontalAlignment: Alignment = Alignment.Center,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = horizontalAlignment,
    ) {
        NavigationItem(
            modifier = modifier,
            imagePainter = imagePainter,
            description = description,
        )
    }
}

@Composable
internal fun NavigationItem(
    modifier: Modifier,
    imagePainter: Painter,
    description: String,
) {
    Column(
        modifier = modifier
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = imagePainter,
            contentDescription = description,
            colorFilter = ColorFilter.tint(MissionTheme.colors.darkSecondary),
            modifier = Modifier.size(40.dp),
        )
        Text(text = description)
    }
}

