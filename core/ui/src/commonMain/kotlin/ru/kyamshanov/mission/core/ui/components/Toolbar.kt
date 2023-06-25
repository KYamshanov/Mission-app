package ru.kyamshanov.mission.core.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntSize
import ru.kyamshanov.mission.MissionTheme

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    title: String,
    navigationListener: () -> Unit,
) = TopBar(
    modifier = modifier,
    navigationListener = navigationListener,
    title = {
        Text(
            modifier = modifier.align(Alignment.Center),
            text = title,
            style = MissionTheme.typography.topBarTitle
        )
    }
)

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    navigationListener: () -> Unit,
) = TopBar(
    modifier = modifier,
    navigationListener = navigationListener,
    title = {
        Column(modifier = Modifier.align(Alignment.Center).fillMaxWidth()) {
            Text(
                modifier = modifier.align(Alignment.CenterHorizontally),
                text = title,
                style = MissionTheme.typography.topBarSecondaryTitle
            )
            Text(
                modifier = modifier.align(Alignment.CenterHorizontally),
                text = subtitle,
                style = MissionTheme.typography.topBarSubtitle
            )
        }
    }
)

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    title: @Composable BoxScope.() -> Unit,
    navigationListener: () -> Unit,
) {
    TopAppBar(
        modifier = modifier,
        backgroundColor = MissionTheme.colors.secondary,
        contentColor = MissionTheme.colors.primary,
   /*     contentPadding = when (LocalViewConfiguration.current.orientation) {
            ORIENTATION_PORTRAIT -> WindowInsets.statusBars.asPaddingValues()
            else -> WindowInsets.systemBars.asPaddingValues()
        }*/
    ) {
        val localDensity = LocalDensity.current
        val iconSizeState = remember { mutableStateOf(IntSize.Zero) }

      /*  Image(
            modifier = Modifier.run {
                padding(10.dp)
                    .clip(CircleShape)
                    .clickable(onClick = navigationListener)
                    .onSizeChanged { iconSizeState.value = it }
            },
            painter = painterResource(id = R.drawable.arrow_back),
            contentDescription = "come back",
            colorFilter = ColorFilter.tint(MissionTheme.colors.primary)
        )*/

        Box(modifier = Modifier.fillMaxSize().padding(end = with(localDensity) { iconSizeState.value.width.toDp() })) {
            title.invoke(this)
        }
    }
}