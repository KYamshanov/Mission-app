package ru.kyamshanov.mission.core.navigation.impl

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import ru.kyamshanov.mission.core.di.impl.Di
import ru.kyamshanov.mission.core.navigation.api.Screen
import ru.kyamshanov.mission.core.navigation.api.di.NavigationComponent
import ru.kyamshanov.mission.core.navigation.impl.di.ModuleComponent

class DefaultRootComponent(
    initialScreen: Screen,
    componentContext: ComponentContext,
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<ScreenConfig>()

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

    init {
        requireNotNull(Di.getInternalComponent<NavigationComponent, ModuleComponent>()).navigatorControllerHolder.stackNavigation =
            navigation
    }

    @Parcelize
    class ScreenConfig(val screen: Screen) : Parcelable
}
