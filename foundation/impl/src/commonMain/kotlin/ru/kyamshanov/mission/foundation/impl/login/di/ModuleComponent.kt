package ru.kyamshanov.mission.foundation.impl.login.di

import ru.kyamshanov.mission.core.di.impl.koin.AbstractComponent
import ru.kyamshanov.mission.core.navigation.api.Screen
import ru.kyamshanov.mission.foundation.api.login.di.AuthenticationComponent
import ru.kyamshanov.mission.foundation.impl.login.domain.AuthenticationInteractor
import ru.kyamshanov.mission.foundation.impl.login.presentation.screen.AuthenticationComposableScreen

internal class ModuleComponent : AbstractComponent(), AuthenticationComponent {

    val authenticationInteractor: AuthenticationInteractor = resolve()

    override val entryScreen: Screen get() = AuthenticationComposableScreen()
}