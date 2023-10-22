package ru.kyamshanov.mission.core.navigation.impl.domain

import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.stack.StackNavigation

interface NavigatorControllerHolder {

    var stackNavigation: StackNavigation<ScreenConfig>?

    var alertNavigation: SlotNavigation<ScreenConfig>?

    var rootComponent: RootComponent?
}