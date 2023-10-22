package ru.kyamshanov.mission.core.navigation.impl.di

import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.stack.StackNavigation
import org.koin.dsl.module
import ru.kyamshanov.mission.core.navigation.api.Navigator
import ru.kyamshanov.mission.core.navigation.api.ResultBus
import ru.kyamshanov.mission.core.navigation.impl.domain.NavigatorControllerHolder
import ru.kyamshanov.mission.core.navigation.impl.domain.NavigatorImpl
import ru.kyamshanov.mission.core.navigation.impl.domain.ResultBusImpl
import ru.kyamshanov.mission.core.navigation.impl.domain.RootComponent
import ru.kyamshanov.mission.core.navigation.impl.domain.ScreenConfig

internal val navigationModule = module {
    single<Navigator> { NavigatorImpl(get(), get()) }
    single<ResultBus> { ResultBusImpl(get()) }
    single<NavigatorControllerHolder> {
        object : NavigatorControllerHolder {
            override var stackNavigation: StackNavigation<ScreenConfig>? = null
            override var alertNavigation: SlotNavigation<ScreenConfig>? = null
            override var rootComponent: RootComponent? = null
        }
    }
}