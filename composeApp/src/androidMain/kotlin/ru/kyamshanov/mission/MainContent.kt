package ru.kyamshanov.mission

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.router.stack.active
import io.github.aakira.napier.Napier
import ru.kyamshanov.mission.core.navigation.common.ComposableScreen
import ru.kyamshanov.mission.core.navigation.common.utils.composeComponentContext
import ru.kyamshanov.mission.core.navigation.impl.domain.RootComponent

@Composable
fun MainContent(defaultRootComponent: RootComponent) {

    MissionTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MissionTheme.colors.background) {
            val active = defaultRootComponent.childStack.active
            val screen = active.instance.screen
            val context = active.instance.context
            when (screen) {
                is ComposableScreen -> {
                    Napier.d("Installing screen : screen::class.java.simpleName")
                    screen.Content(composeComponentContext(context))
                }
            }
        }
    }
}


