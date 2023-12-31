package ru.kyamshanov.mission.core.navigation.impl.di

import ru.kyamshanov.mission.core.di.impl.koin.AbstractComponent
import ru.kyamshanov.mission.core.navigation.api.Navigator
import ru.kyamshanov.mission.core.navigation.api.ResultBus
import ru.kyamshanov.mission.core.navigation.api.di.NavigationComponent
import ru.kyamshanov.mission.core.navigation.impl.domain.NavigatorControllerHolder

class NavigationComponentImpl : AbstractComponent(), NavigationComponent {

    val navigatorControllerHolder: NavigatorControllerHolder = resolve()
    override val navigator: Navigator = resolve()
    override val resultBus: ResultBus = resolve()

}