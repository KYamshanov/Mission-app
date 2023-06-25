package ru.kyamshanov.mission.core.login_screen.impl.di

import ru.kyamshanov.mission.core.login_screen.impl.domain.AuthenticationLauncherImpl

interface AuthenticationComponentImpl {

    val launcher: AuthenticationLauncherImpl
}