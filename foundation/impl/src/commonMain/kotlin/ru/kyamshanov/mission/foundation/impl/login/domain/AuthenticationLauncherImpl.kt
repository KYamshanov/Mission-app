package ru.kyamshanov.mission.foundation.impl.login.domain

import androidx.compose.runtime.Composable
import ru.kyamshanov.mission.core.di.impl.Di
import ru.kyamshanov.mission.foundation.impl.login.composable.AuthenticationComponent
import ru.kyamshanov.mission.core.navigation.api.di.NavigationComponent
import ru.kyamshanov.mission.core.navigation.common.ComposableScreen
import ru.kyamshanov.mission.foundation.api.login.domain.AuthenticationLauncher

class AuthenticationLauncherImpl : AuthenticationLauncher {

    override fun launch() {
        Di.getComponent<NavigationComponent>()!!.navigator.navigateTo(LoginScreen())
    }

    class LoginScreen : ComposableScreen {


        @Composable
        override fun Content() {
            AuthenticationComponent { login, password ->

            }
        }
    }
}