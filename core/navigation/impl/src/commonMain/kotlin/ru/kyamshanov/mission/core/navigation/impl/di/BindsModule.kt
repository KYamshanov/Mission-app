package ru.kyamshanov.mission.core.navigation.impl.di

import org.koin.dsl.module
import ru.kyamshanov.mission.core.navigation.api.Navigator
import ru.kyamshanov.mission.core.navigation.api.ResultProvider
import ru.kyamshanov.mission.core.navigation.impl.domain.NavigatorImpl
import ru.kyamshanov.mission.core.navigation.impl.domain.ResultProviderImpl
import ru.kyamshanov.mission.core.navigation.impl.domain.NavigatorControllerHolder
import cafe.adriel.voyager.navigator.Navigator as VoyagerNavigator

internal val bindsModule = module {
    single<Navigator> { NavigatorImpl(get(), get()) }
    single<ResultProvider> { ResultProviderImpl(get()) }

    single<NavigatorControllerHolder> {
        object : NavigatorControllerHolder {
            override var navigator: VoyagerNavigator? = null
        }
    }
}