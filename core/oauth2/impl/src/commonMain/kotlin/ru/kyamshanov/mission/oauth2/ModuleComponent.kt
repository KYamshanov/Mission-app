package ru.kyamshanov.mission.oauth2

import ru.kyamshanov.mission.core.di.impl.koin.AbstractComponent
import ru.kyamshanov.mission.oauth2.api.AuthenticationInteractor
import ru.kyamshanov.mission.oauth2.api.OAuth2Component


internal class ModuleComponent : AbstractComponent(), OAuth2Component {

    override val authorizationUriProvider: AuthenticationInteractor = resolve()
}