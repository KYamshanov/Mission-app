package ru.kyamshanov.mission.core.navigation.impl

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import ru.kyamshanov.mission.core.navigation.api.Screen

interface RootComponent {

    val childStack: Value<ChildStack<*, Screen>>
}