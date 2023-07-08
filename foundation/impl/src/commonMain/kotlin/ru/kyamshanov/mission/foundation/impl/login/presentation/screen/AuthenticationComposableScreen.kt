package ru.kyamshanov.mission.foundation.impl.login.presentation.screen

import androidx.compose.runtime.Composable
import ru.kyamshanov.mission.core.navigation.common.ComposableScreen
import ru.kyamshanov.mission.foundation.impl.login.presentation.composable.AuthenticationComponent

internal class AuthenticationComposableScreen : ComposableScreen {

    @Composable
    override fun Content() = AuthenticationComponent()
}