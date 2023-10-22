package ru.kyamshanov.mission.authorization.impl.di

import org.koin.core.module.Module
import ru.kyamshanov.mission.authorization.api.AuthorizationComponent
import ru.kyamshanov.mission.core.di.impl.koin.AbstractComponentBuilder
import ru.kyamshanov.mission.oauth2.api.OAuth2Component

class AuthorizationComponentBuilder : AbstractComponentBuilder<AuthorizationComponent>() {

    override val modules: List<Module> = listOf(AuthorizationModule)
    override fun build(): AuthorizationComponent = ModuleComponent()
}