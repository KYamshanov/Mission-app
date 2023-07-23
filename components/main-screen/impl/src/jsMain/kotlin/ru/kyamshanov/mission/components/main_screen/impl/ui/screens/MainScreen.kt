package ru.kyamshanov.mission.components.main_screen.impl.ui.screens

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.Text
import ru.kyamshanov.mission.core.navigation.api.Screen
import ru.kyamshanov.mission.core.navigation.common.JsComposableScreen

internal actual class MainScreen : Screen, JsComposableScreen {

    @Composable
    override fun Content(componentContext: ComponentContext) {
       Div {
           H1 { Text("Главная") }
       }
    }

}