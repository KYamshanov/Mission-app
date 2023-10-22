package ru.kyamshanov.mission.authorization.impl.di

import org.koin.dsl.bind
import org.koin.dsl.module
import ru.kyamshanov.mission.authorization.api.AuthorizationLauncher
import ru.kyamshanov.mission.authorization.impl.presentation.launcher.AuthorizationLauncherImpl
import ru.kyamshanov.mission.oauth2.api.AuthenticationInteractor

internal val AuthorizationModule = module {
    single { AuthorizationLauncherImpl(get()) } bind AuthorizationLauncher::class
}