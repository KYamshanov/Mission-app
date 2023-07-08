package ru.kyamshanov.mission.foundation.impl.login.presentation.navigation

import ru.kyamshanov.mission.core.di.impl.Di
import ru.kyamshanov.mission.core.navigation.api.di.NavigationComponent
import ru.kyamshanov.mission.foundation.api.login.domain.AuthenticationLauncher
import ru.kyamshanov.mission.foundation.impl.login.presentation.screen.AuthenticationComposableScreen

class AuthenticationLauncherImpl : AuthenticationLauncher {

    override fun launch() {
        requireNotNull(Di.getComponent<NavigationComponent>()).navigator.navigateTo(AuthenticationComposableScreen())
    }
}