package ru.kyamshanov.mission.core.navigation.impl.domain

import cafe.adriel.voyager.navigator.Navigator as VNavigator

interface NavigatorControllerHolder {

    var navigator: VNavigator?
}