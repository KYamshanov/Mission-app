package ru.kyamshanov.mission.core.login_screen.api.di

import ru.kyamshanov.mission.core.login_screen.api.domain.AuthenticationLauncher

interface AuthenticationComponent {

    val launcher: AuthenticationLauncher
}