package ru.kyamshanov.mission.core.login_screen.impl.domain

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import ru.kyamshanov.mission.core.di.impl.Di
import ru.kyamshanov.mission.core.login_screen.api.domain.AuthenticationLauncher
import ru.kyamshanov.mission.core.login_screen.impl.ui.composable.AuthenticationComponent
import ru.kyamshanov.mission.core.navigation.api.di.NavigationComponent
import ru.kyamshanov.mission.core.navigation.common.ComposableScreen

class AuthenticationLauncherImpl : AuthenticationLauncher {

    override fun launch() {
        Di.getComponent<NavigationComponent>()!!.navigator.navigateTo(LoginScreen())
    }

    class LoginScreen : ComposableScreen {


        @Composable
        override fun Content() {
            AuthenticationComponent { _, _ -> }
        }
    }
}