package ru.kyamshanov.mission.core.login_screen.impl.domain

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import ru.kyamshanov.mission.core.login_screen.impl.ui.composable.AuthenticationComponent

object AuthenticationLauncherImpl {

    var nav: Navigator? = null

    fun launch() {
        nav!!.push(LoginScreen())
    }

    class LoginScreen : Screen {


        @Composable
        override fun Content() {
            AuthenticationComponent { _, _ -> }
        }
    }
}