package ru.kyamshanov.mission.core.navigation.impl.di

import com.arkivanov.decompose.router.stack.StackNavigation
import org.koin.dsl.module
import ru.kyamshanov.mission.core.navigation.api.Navigator
import ru.kyamshanov.mission.core.navigation.api.ResultProvider
import ru.kyamshanov.mission.core.navigation.impl.DefaultRootComponent
import ru.kyamshanov.mission.core.navigation.impl.domain.NavigatorImpl
import ru.kyamshanov.mission.core.navigation.impl.domain.ResultProviderImpl
import ru.kyamshanov.mission.core.navigation.impl.domain.NavigatorControllerHolder
import cafe.adriel.voyager.navigator.Navigator as VoyagerNavigator

internal val navigationModule = module {
    scope<ModuleComponent> {
        scoped<Navigator> { NavigatorImpl(get(), get()) }
        scoped<ResultProvider> { ResultProviderImpl(get()) }
        scoped<NavigatorControllerHolder> {
            object : NavigatorControllerHolder {
                override var navigator: VoyagerNavigator? = null
                override var stack: StackNavigation<DefaultRootComponent.ScreenConfig>? = null
            }
        }
    }
}