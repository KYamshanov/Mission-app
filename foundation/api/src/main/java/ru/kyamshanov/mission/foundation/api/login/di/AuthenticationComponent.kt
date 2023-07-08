package ru.kyamshanov.mission.foundation.api.login.di

import ru.kyamshanov.mission.foundation.api.login.domain.AuthenticationLauncher

interface AuthenticationComponent {

    val launcher: AuthenticationLauncher
}