package ru.kyamshanov.mission.authorization.impl.presentation.launcher

import ru.kyamshanov.mission.authorization.api.AuthorizationLauncher
import ru.kyamshanov.mission.authorization.impl.presentation.screen.LoginScreen
import ru.kyamshanov.mission.core.navigation.api.Navigator


internal class AuthorizationLauncherImpl(
    private val navigator: Navigator
) : AuthorizationLauncher {

    override fun launch() {
        navigator.newRootScreen(LoginScreen())
    }
}