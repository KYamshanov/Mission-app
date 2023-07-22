package ru.kyamshanov.mission.foundation.impl.login.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.arkivanov.decompose.ComponentContext
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.display
import org.jetbrains.compose.web.css.padding
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.Input
import org.jetbrains.compose.web.dom.Text
import ru.kyamshanov.mission.core.navigation.api.Screen
import ru.kyamshanov.mission.core.navigation.common.JsComposableScreen
import ru.kyamshanov.mission.foundation.impl.login.presentation.component.AuthenticationUiComponent

internal actual class AuthenticationComposableScreen : Screen, JsComposableScreen {

    @Composable
    override fun Content(componentContext: ComponentContext) {
        val viewModel = AuthenticationUiComponent(componentContext).viewModel
        val login = remember { mutableStateOf("") }
        viewModel.loginState.subscribe { login.value = it }

        val password = remember { mutableStateOf("") }
        viewModel.passwordState.subscribe { password.value = it.toString() }

        Div {
            H1 { Text("Авторизация, сука") }

            Input(
                type = InputType.Text,
                attrs = {
                    attr("placeholder", "Введи логин, идиот")
                    value(login.value)
                    onInput { event ->
                        viewModel.setLogin(event.value)
                    }
                    style {
                        display(DisplayStyle.Inline)
                        padding(4.px)
                    }
                }
            )

            Input(
                type = InputType.Text,
                attrs = {
                    attr("placeholder", "Введи пароль, идиот")
                    value(password.value)
                    onInput { event ->
                        viewModel.setPassword(event.value)
                    }
                    style {
                        display(DisplayStyle.Inline)
                        padding(4.px)
                    }
                }
            )

            Button(
                attrs = {
                    attr("placeholder", "Введи пароль, идиот")
                    onClick { viewModel.clickOnLogin() }
                    style {
                        padding(4.px)
                    }
                }
            )
        }
    }

}