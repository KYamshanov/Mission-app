package ru.kyamshanov.mission.core.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.arkivanov.decompose.router.stack.active
import io.github.aakira.napier.Napier
import ru.kyamshanov.mission.MissionTheme
import ru.kyamshanov.mission.core.navigation.api.Screen
import ru.kyamshanov.mission.core.navigation.common.ComposableScreen
import ru.kyamshanov.mission.core.navigation.impl.DefaultRootComponent
import ru.kyamshanov.mission.core.navigation.impl.domain.RootComponent

@Composable
fun MainContent(defaultRootComponent: RootComponent) {
    MissionTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MissionTheme.colors.background) {
            Children(
                stack = defaultRootComponent.childStack
            ) { active ->
                val screen = active.instance.screen
                val context = active.instance.context
                when (screen) {
                    is ComposableScreen -> {
                        screen.Content(context)
                    }
                }
            }
        }
    }
}

