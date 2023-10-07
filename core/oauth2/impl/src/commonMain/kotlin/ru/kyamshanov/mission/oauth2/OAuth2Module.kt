package ru.kyamshanov.mission.oauth2

import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.kyamshanov.mission.oauth2.api.AuthenticationInteractor

internal val OAuth2Module = module {
    single { AuthenticationApiImpl(get(named("oauth2"))) } bind AuthenticationApi::class
    factory { AuthenticationInteractorImpl(get()) } bind AuthenticationInteractor::class

}