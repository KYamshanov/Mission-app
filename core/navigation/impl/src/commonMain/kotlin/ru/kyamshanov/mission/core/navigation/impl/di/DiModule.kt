package ru.kyamshanov.mission.core.navigation.impl.di

import com.arkivanov.decompose.router.stack.StackNavigation
import org.koin.dsl.module
import ru.kyamshanov.mission.core.navigation.api.Navigator
import ru.kyamshanov.mission.core.navigation.api.ResultProvider
import ru.kyamshanov.mission.core.navigation.impl.domain.NavigatorControllerHolder
import ru.kyamshanov.mission.core.navigation.impl.domain.NavigatorImpl
import ru.kyamshanov.mission.core.navigation.impl.domain.ResultProviderImpl
import ru.kyamshanov.mission.core.navigation.impl.domain.RootComponent
import ru.kyamshanov.mission.core.navigation.impl.domain.ScreenConfig

internal val navigationModule = module {
    scope<NavigationComponentImpl> {
        scoped<Navigator> { NavigatorImpl(get(), get()) }
        scoped<ResultProvider> { ResultProviderImpl(get()) }
        scoped<NavigatorControllerHolder> {
            object : NavigatorControllerHolder {
                override var stackNavigation: StackNavigation<ScreenConfig>? =
                    null
                override var rootComponent: RootComponent? = null
            }
        }
    }
}