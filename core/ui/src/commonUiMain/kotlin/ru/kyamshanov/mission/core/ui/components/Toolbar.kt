package ru.kyamshanov.mission.core.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalViewConfiguration
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import dev.icerock.moko.resources.compose.painterResource
import ru.kyamshanov.mission.MissionTheme
import ru.kyamshanov.mission.core.ui.Res
import ru.kyamshanov.mission.core.ui.constants.ORIENTATION_PORTRAIT
import ru.kyamshanov.mission.core.ui.extensions.getOrientation
import ru.kyamshanov.mission.core.ui.extensions.getStatusBars
import kotlin.math.max

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    title: String,
    navigationListener: () -> Unit,
    trailingContent: @Composable (() -> Unit)? = null
) = TopBar(
    modifier = modifier,
    navigationListener = navigationListener,
    title = {
        Text(
            modifier = modifier.align(Alignment.Center),
            text = title,
            style = MissionTheme.typography.topBarTitle
        )
    },
    trailingContent = trailingContent,
)

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    navigationListener: () -> Unit,
    trailingContent: @Composable (() -> Unit)? = null
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
    },
    trailingContent = trailingContent,
)

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    title: @Composable BoxScope.() -> Unit,
    navigationListener: () -> Unit,
    trailingContent: @Composable (() -> Unit)? = null
) {
    TopAppBar(
        modifier = modifier,
        backgroundColor = MissionTheme.colors.secondary,
        contentColor = MissionTheme.colors.primary,
        contentPadding = when (LocalViewConfiguration.current.getOrientation()) {
            ORIENTATION_PORTRAIT -> WindowInsets.getStatusBars().asPaddingValues()
            else -> WindowInsets.getStatusBars().asPaddingValues()
        }
    ) {
        val localDensity = LocalDensity.current
        val iconSizeState = remember { mutableStateOf(IntSize.Zero) }
        val trailingContentSizeState = remember { mutableStateOf(IntSize.Zero) }

        Image(
            modifier = Modifier.run {
                padding(10.dp)
                    .clip(CircleShape)
                    .clickable(onClick = navigationListener)
                    .onSizeChanged { iconSizeState.value = it }
            },
            painter = painterResource(Res.images.arrow_back),
            contentDescription = "come back",
            colorFilter = ColorFilter.tint(MissionTheme.colors.primary)
        )

        Box(
            modifier = Modifier.weight(1f, true)
                .padding(
                    end = with(localDensity) {
                        max(0, (iconSizeState.value.width - trailingContentSizeState.value.width))
                            .toDp()
                    },
                )
        ) {
            title.invoke(this)
        }

        if (trailingContent != null) {
            Box(modifier = Modifier.onSizeChanged { trailingContentSizeState.value = it }) {
                trailingContent.invoke()
            }
        }
    }
}