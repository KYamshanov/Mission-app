package ru.kyamshanov.mission.core.login_screen.impl.di

import ru.kyamshanov.mission.core.di.impl.koin.AbstractComponent
import ru.kyamshanov.mission.core.login_screen.api.di.AuthenticationComponent
import ru.kyamshanov.mission.core.login_screen.api.domain.AuthenticationLauncher
import ru.kyamshanov.mission.core.navigation.api.Navigator

internal class ModuleComponent : AbstractComponent(), AuthenticationComponent {


    override val launcher: AuthenticationLauncher = resolve()
}