package ru.kyamshanov.mission.foundation.impl.login.di

import org.koin.core.module.Module
import ru.kyamshanov.mission.core.di.impl.koin.AbstractComponentBuilder
import ru.kyamshanov.mission.foundation.api.login.di.AuthenticationComponent

class AuthenticationComponentBuilder : AbstractComponentBuilder<AuthenticationComponent>() {

    override val modules: List<Module> = listOf(authenticationModule)

    override fun build(): AuthenticationComponent = ModuleComponent()
}