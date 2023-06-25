package ru.kyamshanov.mission.session_front.impl.di

import org.koin.dsl.bind
import org.koin.dsl.module
import ru.kyamshanov.mission.core.base.api.di.BaseCoreComponent
import ru.kyamshanov.mission.core.di.impl.Di
import ru.kyamshanov.mission.core.network.api.di.NetworkComponent
import ru.kyamshanov.mission.session_front.api.SessionFront
import ru.kyamshanov.mission.session_front.api.SessionInfo
import ru.kyamshanov.mission.session_front.impl.SessionInfoImpl
import ru.kyamshanov.mission.session_front.impl.data.JwtLoginInteractorImpl
import ru.kyamshanov.mission.session_front.impl.data.api.AuthenticationApi
import ru.kyamshanov.mission.session_front.impl.data.api.AuthenticationApiImpl
import ru.kyamshanov.mission.session_front.impl.data.api.IdentifyApi
import ru.kyamshanov.mission.session_front.impl.data.api.IdentifyApiImpl
import ru.kyamshanov.mission.session_front.impl.data.usecase.IdentifyUserUseCaseImpl
import ru.kyamshanov.mission.session_front.impl.domain.JwtLoginInteractor
import ru.kyamshanov.mission.session_front.impl.domain.usecase.IdentifyUserUseCase
import ru.kyamshanov.mission.session_front.impl.ui.SessionFrontImpl

internal val SessionFrontModule = module {
    single { requireNotNull(Di.getComponent<NetworkComponent>()).requestFactory }
    single { requireNotNull(Di.getComponent<BaseCoreComponent>()).missionPreferences }
    single { requireNotNull(Di.getComponent<BaseCoreComponent>()).device }

    single { SessionInfoImpl() } bind SessionInfo::class
    single<AuthenticationApi> { AuthenticationApiImpl(get()) }
    single { SessionFrontImpl(get(), get(), get(), get()) } bind SessionFront::class
    single<JwtLoginInteractor> { JwtLoginInteractorImpl(get(), get()) }
    single<IdentifyApi> { IdentifyApiImpl(get()) }
    single<IdentifyUserUseCase> { IdentifyUserUseCaseImpl(get()) }

}