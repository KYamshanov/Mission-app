package ru.kyamshanov.mission.core.navigation.impl.di

import ru.kyamshanov.mission.core.di.impl.koin.AbstractComponent
import ru.kyamshanov.mission.core.navigation.api.Navigator
import ru.kyamshanov.mission.core.navigation.api.ResultProvider
import ru.kyamshanov.mission.core.navigation.api.di.NavigationComponent
import ru.kyamshanov.mission.core.navigation.impl.domain.NavigatorControllerHolder

internal class ModuleComponent : AbstractComponent(), NavigationComponent {

    val navigatorControllerHolder: NavigatorControllerHolder = resolve()
    override val navigator: Navigator = resolve()
    override val resultProvider: ResultProvider = resolve()

}