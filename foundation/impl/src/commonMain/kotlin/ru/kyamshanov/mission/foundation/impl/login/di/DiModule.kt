package ru.kyamshanov.mission.foundation.impl.login.di

import org.koin.dsl.module
import ru.kyamshanov.mission.components.main_screen.api.di.MainScreenComponent
import ru.kyamshanov.mission.core.di.impl.Di
import ru.kyamshanov.mission.foundation.impl.login.presentation.navigation.AuthenticationLauncherImpl
import ru.kyamshanov.mission.foundation.api.login.domain.AuthenticationLauncher
import ru.kyamshanov.mission.foundation.impl.login.data.AuthenticationInteractorImpl
import ru.kyamshanov.mission.foundation.impl.login.domain.AuthenticationInteractor
import ru.kyamshanov.mission.foundation.impl.login.presentation.viewmodel.AuthenticationViewModel

internal val authenticationModule = module {
    scope<ModuleComponent> {
        scoped<AuthenticationLauncher> { AuthenticationLauncherImpl() }
        scoped<AuthenticationInteractor> { AuthenticationInteractorImpl(get()) }
        scoped {
            AuthenticationViewModel(get(), requireNotNull(Di.getComponent<MainScreenComponent>()).launcher)
        }
    }
}