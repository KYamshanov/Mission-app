package ru.kyamshanov.mission.authorization.impl.di

import ru.kyamshanov.mission.authorization.api.AuthorizationComponent
import ru.kyamshanov.mission.authorization.api.AuthorizationLauncher
import ru.kyamshanov.mission.core.di.impl.koin.AbstractComponent
import ru.kyamshanov.mission.oauth2.api.AuthenticationInteractor
import ru.kyamshanov.mission.oauth2.api.OAuth2Component


internal class ModuleComponent : AbstractComponent(), AuthorizationComponent {
    override val launcher: AuthorizationLauncher = resolve()
}