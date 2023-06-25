package ru.kyamshanov.mission.core.login_screen.impl.di

import org.koin.dsl.module
import ru.kyamshanov.mission.core.login_screen.api.domain.AuthenticationLauncher
import ru.kyamshanov.mission.core.login_screen.impl.domain.AuthenticationLauncherImpl

internal val authenticationModule = module {
    single<AuthenticationLauncher> { AuthenticationLauncherImpl }
}