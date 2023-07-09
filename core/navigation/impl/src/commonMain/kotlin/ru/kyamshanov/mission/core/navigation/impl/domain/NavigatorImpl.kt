package ru.kyamshanov.mission.core.navigation.impl.domain

import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.router.stack.replaceAll
import ru.kyamshanov.mission.core.navigation.api.NavigationBoundaryData
import ru.kyamshanov.mission.core.navigation.api.Navigator
import ru.kyamshanov.mission.core.navigation.api.ResultProvider
import ru.kyamshanov.mission.core.navigation.api.Screen

internal class NavigatorImpl(
    private val controllerHolder: NavigatorControllerHolder,
    private val resultProvider: ResultProvider,
) : Navigator {

    private val stackNavigation get() = requireNotNull(controllerHolder.stackNavigation) { "StackNavigation cannot be null" }

    override fun navigateTo(screen: Screen) {
        stackNavigation.push(ScreenConfig(screen))
    }

    override fun replaceTo(screen: Screen) {
        stackNavigation.push(ScreenConfig(screen))
    }

    override fun newRootScreen(screen: Screen) {
        stackNavigation.replaceAll(ScreenConfig(screen))
    }

    override fun exit() {
        stackNavigation.pop()
    }

    override fun <ReturnDataType : NavigationBoundaryData?> backWithResult(
        key: String,
        data: ReturnDataType
    ) {
        //TODO переписать
        stackNavigation.pop()
        resultProvider.notify(key)
    }
}