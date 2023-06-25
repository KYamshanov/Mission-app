package ru.kyamshanov.mission.core.login_screen.impl.di

import org.koin.core.module.Module
import ru.kyamshanov.mission.core.di.impl.koin.AbstractComponentBuilder
import ru.kyamshanov.mission.core.login_screen.api.di.AuthenticationComponent

class AuthenticationComponentBuilder : AbstractComponentBuilder<AuthenticationComponent>() {

    override val modules: List<Module> = listOf(authenticationModule)


    override fun build() = AuthenticationComponentImpl()


}