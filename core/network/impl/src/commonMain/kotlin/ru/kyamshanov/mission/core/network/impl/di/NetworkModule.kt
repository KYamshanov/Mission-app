package ru.kyamshanov.mission.core.network.impl.di

import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.kyamshanov.mission.core.di.impl.Di
import ru.kyamshanov.mission.core.network.api.RequestFactory
import ru.kyamshanov.mission.core.network.impl.RequestFactoryImpl
import ru.kyamshanov.mission.session_front.api.di.SessionFrontComponent

internal val NetworkModule = module {
    single<RequestFactory> { RequestFactoryImpl() }
    single<RequestFactory>(named("oauth2")) { RequestFactoryImpl(true) }
}