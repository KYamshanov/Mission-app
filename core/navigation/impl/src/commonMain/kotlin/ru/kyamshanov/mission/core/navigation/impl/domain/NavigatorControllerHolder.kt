package ru.kyamshanov.mission.core.navigation.impl.domain

import com.arkivanov.decompose.router.stack.StackNavigation
import ru.kyamshanov.mission.core.navigation.impl.DefaultRootComponent

interface NavigatorControllerHolder {

    var stackNavigation: StackNavigation<DefaultRootComponent.ScreenConfig>?
}