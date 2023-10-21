package ru.kyamshanov.mission.core.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.childContext
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import ru.kyamshanov.mission.MissionTheme
import ru.kyamshanov.mission.core.navigation.common.ComposableScreen
import ru.kyamshanov.mission.core.navigation.common.utils.composeComponentContext
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

            val alert by defaultRootComponent.alertSlot.subscribeAsState()
            alert.child?.instance?.let {
                when (val screen = it.screen) {
                    is ComposableScreen -> {
                        Box(modifier = Modifier.fillMaxSize()) {
                            screen.Content(it.context)
                        }
                    }
                }
            }

        }
    }
}

