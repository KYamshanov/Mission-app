package ru.kyamshanov.mission.session_front.impl.di

import org.koin.dsl.bind
import org.koin.dsl.module
import ru.kyamshanov.mission.session_front.api.SessionFront
import ru.kyamshanov.mission.session_front.api.SessionInfo
import ru.kyamshanov.mission.session_front.impl.domain.session.SessionInfoImpl
import ru.kyamshanov.mission.session_front.impl.data.api.AuthenticationApi
import ru.kyamshanov.mission.session_front.impl.data.api.AuthenticationApiImpl
import ru.kyamshanov.mission.session_front.impl.data.api.IdentifyApi
import ru.kyamshanov.mission.session_front.impl.data.api.IdentifyApiImpl
import ru.kyamshanov.mission.session_front.impl.data.usecase.IdentifyUserUseCaseImpl
import ru.kyamshanov.mission.session_front.impl.domain.usecase.IdentifyUserUseCase
import ru.kyamshanov.mission.session_front.impl.domain.session.SessionFrontImpl

internal val SessionFrontModule = module {
    single { SessionInfoImpl() } bind SessionInfo::class
    single<AuthenticationApi> { AuthenticationApiImpl(get()) }
    single { SessionFrontImpl(get(), get(), get(), get()) } bind SessionFront::class
    single<IdentifyApi> { IdentifyApiImpl(get()) }
    single<IdentifyUserUseCase> { IdentifyUserUseCaseImpl(get()) }
}