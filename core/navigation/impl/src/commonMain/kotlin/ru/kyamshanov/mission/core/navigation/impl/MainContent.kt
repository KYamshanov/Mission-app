package ru.kyamshanov.mission.core.navigation.impl

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import ru.kyamshanov.mission.MissionTheme
import ru.kyamshanov.mission.core.navigation.api.Screen
import ru.kyamshanov.mission.core.navigation.common.ComposableScreen

@Composable
fun MainContent(rootComponentContext: ComponentContext, initialScreen: Screen) {
    val defaultRootComponent = remember {
        DefaultRootComponent(
            initialScreen = initialScreen,
            componentContext = rootComponentContext
        )
    }



    MissionTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MissionTheme.colors.background) {
            Children(
                stack = defaultRootComponent.childStack
            ) { active ->
                val screen = active.instance.screen
                val context = active.instance.context
                when (screen) {
                    is ComposableScreen -> {
                        println("Replace content ${screen::class.java.simpleName}")
                        screen.Content(context)
                    }
                }
            }
        }
    }
}


