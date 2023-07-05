package ru.kyamshanov.mission.foundation.impl.login.di

import org.koin.dsl.module
import ru.kyamshanov.mission.foundation.impl.login.domain.AuthenticationLauncherImpl
import ru.kyamshanov.mission.foundation.api.login.domain.AuthenticationLauncher

internal val authenticationModule = module {
    scope<ModuleComponent> {
        scoped<AuthenticationLauncher> { AuthenticationLauncherImpl() }
    }
}