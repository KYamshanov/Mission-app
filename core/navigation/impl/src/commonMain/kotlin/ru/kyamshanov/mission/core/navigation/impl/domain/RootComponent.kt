package ru.kyamshanov.mission.core.navigation.impl.domain

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import ru.kyamshanov.mission.core.navigation.api.Screen

interface RootComponent {

    val childStack: Value<ChildStack<*, ScreenWithContext>>

    data class ScreenWithContext(
        val screen: Screen,
        val context: ComponentContext
    )
}