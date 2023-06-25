package ru.kyamshanov.mission.core.navigation.api.di

import ru.kyamshanov.mission.core.di.api.CoreComponent
import ru.kyamshanov.mission.core.navigation.api.Navigator
import ru.kyamshanov.mission.core.navigation.api.ResultProvider

interface NavigationComponent : CoreComponent {

    val navigator: Navigator

    val resultProvider: ResultProvider
}