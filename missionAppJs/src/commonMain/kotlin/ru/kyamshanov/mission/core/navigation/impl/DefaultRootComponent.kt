package ru.kyamshanov.mission.core.navigation.impl

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import ru.kyamshanov.mission.core.di.impl.Di
import ru.kyamshanov.mission.core.navigation.api.Screen
import ru.kyamshanov.mission.core.navigation.api.di.NavigationComponent
import ru.kyamshanov.mission.core.navigation.impl.di.NavigationComponentImpl
import ru.kyamshanov.mission.core.navigation.impl.domain.RootComponent
import ru.kyamshanov.mission.core.navigation.impl.domain.ScreenConfig

class DefaultRootComponent(
    initialScreen: Screen,
    componentContext: ComponentContext,
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<ScreenConfig>()
    private val alertNavigation = SlotNavigation<ScreenConfig>()

    private val stack =
        childStack(
            source = navigation,
            initialStack = { listOf(ScreenConfig(initialScreen)) },
            handleBackButton = true,
            childFactory = { config, componentContext ->
                RootComponent.ScreenWithContext(config.screen, componentContext)
            },
        )

    override val childStack: Value<ChildStack<*, RootComponent.ScreenWithContext>> =
        stack

    private val alert =
        childSlot(
            source = alertNavigation,
            persistent = true,
            handleBackButton = false,
        ) { config, childComponentContext ->

            RootComponent.ScreenWithContext(config.screen, childComponentContext)
        }

    override val alertSlot = alert

    init {
        requireNotNull(Di.getInternalComponent<NavigationComponent, NavigationComponentImpl>()).navigatorControllerHolder.stackNavigation =
            navigation

        requireNotNull(Di.getInternalComponent<NavigationComponent, NavigationComponentImpl>()).navigatorControllerHolder.alertNavigation =
            alertNavigation
    }
}
