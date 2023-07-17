package ru.kyamshanov.mission.core.navigation.common.utils

import ru.kyamshanov.mission.core.di.impl.Di
import ru.kyamshanov.mission.core.navigation.api.Navigator
import ru.kyamshanov.mission.core.navigation.api.di.UiComponent

inline fun <reified ComponentType : UiComponent> Navigator.newRootScreen() {
    val component = requireNotNull(Di.getComponent<ComponentType>())
    newRootScreen(component.entryScreen)
}
