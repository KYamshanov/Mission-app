package ru.kyamshanov.mission.core.navigation.impl.domain

import ru.kyamshanov.mission.core.navigation.api.NavigationBoundaryData
import ru.kyamshanov.mission.core.navigation.api.Navigator
import ru.kyamshanov.mission.core.navigation.api.ResultProvider
import ru.kyamshanov.mission.core.navigation.api.Screen
import ru.kyamshanov.mission.core.navigation.common.ComposableScreen

internal class NavigatorImpl(
    private val controllerHolder: NavigatorControllerHolder,
    private val resultProvider: ResultProvider,
) : Navigator {

    private val controller: cafe.adriel.voyager.navigator.Navigator
        get() = requireNotNull(controllerHolder.navigator) { "Navigator controller cannot be null" }

    override fun navigateTo(screen: Screen) {
        val controller = requireNotNull(controllerHolder.navigator) { "Navigator controller cannot be null" }
        when (screen) {
            is ComposableScreen -> controller.push(screen)
            else -> throw IllegalStateException("Screen implementation isn`t be able to navigate")
        }
    }

    override fun replaceTo(screen: Screen) {
        val controller = requireNotNull(controllerHolder.navigator) { "Navigator controller cannot be null" }
        when (screen) {
            is ComposableScreen -> {
                //TODO переписать
                controller.push(screen)
            }

            else -> throw IllegalStateException("Screen implementation isn`t be able to navigate")
        }
    }

    override fun exit() {
        val controller = requireNotNull(controllerHolder.navigator) { "Navigator controller cannot be null" }
        controller.pop()
    }

    override fun <ReturnDataType : NavigationBoundaryData?> backWithResult(key: String, data: ReturnDataType) {
        val controller = requireNotNull(controllerHolder.navigator) { "Navigator controller cannot be null" }
        //TODO переписать
        controller.pop()
        resultProvider.notify(key)
    }
}