package ru.kyamshanov.mission.foundation.impl.login.di

import org.koin.dsl.module
import ru.kyamshanov.mission.foundation.api.login.domain.AuthenticationLauncher
import ru.kyamshanov.mission.foundation.impl.login.data.AuthenticationInteractorImpl
import ru.kyamshanov.mission.foundation.impl.login.domain.AuthenticationInteractor
import ru.kyamshanov.mission.foundation.impl.login.presentation.navigation.AuthenticationLauncherImpl

internal val authenticationModule = module {
    scope<ModuleComponent> {
        scoped<AuthenticationLauncher> { AuthenticationLauncherImpl() }
        scoped<AuthenticationInteractor> { AuthenticationInteractorImpl(get()) }
    }
}