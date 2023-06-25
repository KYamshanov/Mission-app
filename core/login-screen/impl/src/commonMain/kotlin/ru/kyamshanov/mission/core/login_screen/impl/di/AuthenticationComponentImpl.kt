package ru.kyamshanov.mission.core.login_screen.impl.di

import ru.kyamshanov.mission.core.di.impl.koin.AbstractComponent
import ru.kyamshanov.mission.core.di.impl.koin.provide
import ru.kyamshanov.mission.core.login_screen.api.di.AuthenticationComponent
import ru.kyamshanov.mission.core.login_screen.api.domain.AuthenticationLauncher
import ru.kyamshanov.mission.core.login_screen.impl.domain.AuthenticationLauncherImpl

class AuthenticationComponentImpl : AbstractComponent, AuthenticationComponent{
    override val modules: List<org.koin.core.module.Module>
        get() = TODO("Not yet implemented")


    override val launcher: AuthenticationLauncher = provide()
}