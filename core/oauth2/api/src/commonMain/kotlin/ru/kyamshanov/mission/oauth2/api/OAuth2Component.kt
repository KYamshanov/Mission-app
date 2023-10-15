package ru.kyamshanov.mission.oauth2.api

import ru.kyamshanov.mission.core.di.api.CoreComponent

interface OAuth2Component : CoreComponent {

    val authorizationUriProvider: AuthenticationInteractor
}