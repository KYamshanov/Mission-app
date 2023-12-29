package ru.kyamshanov.mission.foundation.impl.splash_screen.presentation.screens

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import dev.icerock.moko.resources.compose.stringResource
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.Img
import org.jetbrains.compose.web.dom.Text
import ru.kyamshanov.mission.TextStyles
import ru.kyamshanov.mission.core.navigation.api.Screen
import ru.kyamshanov.mission.core.navigation.common.JsComposableScreen
import ru.kyamshanov.mission.core.ui.Res
import ru.kyamshanov.mission.foundation.impl.splash_screen.presentation.component.SplashScreenDComponent

internal actual class SplashScreen : Screen, JsComposableScreen {

    @Composable
    override fun Content(componentContext: ComponentContext) {
        SplashScreenDComponent(componentContext).viewModel

        Div(attrs = {
            style {
                width(100.percent)
                display(DisplayStyle.Flex)
                justifyContent(JustifyContent.Center)
            }
        }) {
            Img(Res.images.app_icon.fileUrl, stringResource(Res.strings.app_name), attrs = {
                style {
                    maxWidth(10.percent)
                    maxHeight(10.percent)
                }
            })
        }
        Div(attrs = {
            style {
                width(100.percent)
                display(DisplayStyle.Flex)
                justifyContent(JustifyContent.Center)
            }
        }) {
            H1(attrs = { classes(TextStyles.titleText) }) {
                Text(stringResource(Res.strings.app_name))
            }
        }
    }

}