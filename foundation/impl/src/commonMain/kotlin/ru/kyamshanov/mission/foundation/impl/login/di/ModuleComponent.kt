package ru.kyamshanov.mission.foundation.impl.login.di

import ru.kyamshanov.mission.core.di.impl.koin.AbstractComponent
import ru.kyamshanov.mission.foundation.api.login.di.AuthenticationComponent
import ru.kyamshanov.mission.foundation.api.login.domain.AuthenticationLauncher

internal class ModuleComponent : AbstractComponent(), AuthenticationComponent {

    override val launcher: AuthenticationLauncher = resolve()
}