package ru.kyamshanov.mission.core.navigation.impl.domain

import com.arkivanov.decompose.router.stack.StackNavigation
import ru.kyamshanov.mission.core.navigation.impl.DefaultRootComponent
import cafe.adriel.voyager.navigator.Navigator as VNavigator

interface NavigatorControllerHolder {

    var navigator: VNavigator?

    var stack: StackNavigation<DefaultRootComponent.ScreenConfig>?
}