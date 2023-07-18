package ru.kyamshanov.mission.foundation.impl.login.di

import org.koin.dsl.module
import ru.kyamshanov.mission.foundation.impl.login.data.AuthenticationInteractorImpl
import ru.kyamshanov.mission.foundation.impl.login.domain.AuthenticationInteractor

internal val authenticationModule = module {
    scope<ModuleComponent> {
        scoped<AuthenticationInteractor> { AuthenticationInteractorImpl(get()) }
    }
}