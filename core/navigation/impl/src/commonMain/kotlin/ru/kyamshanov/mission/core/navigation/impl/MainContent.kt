package ru.kyamshanov.mission.core.navigation.impl

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import ru.kyamshanov.mission.MissionTheme
import ru.kyamshanov.mission.core.navigation.common.ComposableScreen

@Composable
fun MainContent(defaultRootComponent: DefaultRootComponent) {
    val stack = defaultRootComponent.childStack.subscribeAsState()

    MissionTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MissionTheme.colors.background) {
            when (val screen = stack.value.active.instance) {
                is ComposableScreen -> screen.Content()
            }
        }
    }


}


