package ru.kyamshanov.mission.core.navigation.impl.domain

import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.router.stack.replaceCurrent
import ru.kyamshanov.mission.core.navigation.api.NavigationBoundaryData
import ru.kyamshanov.mission.core.navigation.api.Navigator
import ru.kyamshanov.mission.core.navigation.api.ResultBus
import ru.kyamshanov.mission.core.navigation.api.Screen

internal class NavigatorImpl(
    private val controllerHolder: NavigatorControllerHolder,
    private val resultBus: ResultBus,
) : Navigator {

    private val stackNavigation get() = requireNotNull(controllerHolder.stackNavigation) { "StackNavigation cannot be null" }
    private val alertNavigation get() = requireNotNull(controllerHolder.alertNavigation) { "AlertNavigation cannot be null" }

    override fun navigateTo(screen: Screen) {
        stackNavigation.push(ScreenConfig(screen))
    }

    override fun replaceTo(screen: Screen) {
        stackNavigation.replaceCurrent(ScreenConfig(screen))
    }

    override fun newRootScreen(screen: Screen) {
        stackNavigation.replaceAll(ScreenConfig(screen))
    }

    override fun exit() {
        stackNavigation.pop()
    }

    override fun alert(screen: Screen) {
        alertNavigation.activate(ScreenConfig(screen))
    }

    override fun dismissAlert() {
        println("Dismiss")
        alertNavigation.dismiss()
    }

    override fun <ReturnDataType : NavigationBoundaryData?> backWithResult(
        key: String,
        data: ReturnDataType
    ) {
        //TODO переписать
        stackNavigation.pop()
        resultBus.notify(key)
    }
}