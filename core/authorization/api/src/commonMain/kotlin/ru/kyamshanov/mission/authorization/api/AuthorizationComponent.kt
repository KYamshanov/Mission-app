package ru.kyamshanov.mission.authorization.api

import ru.kyamshanov.mission.core.di.api.CoreComponent

interface AuthorizationComponent : CoreComponent {

    val launcher: AuthorizationLauncher
}