package ru.kyamshanov.mission.core.network.api.di

import ru.kyamshanov.mission.core.di.api.CoreComponent
import ru.kyamshanov.mission.core.network.api.RequestFactory

interface NetworkComponent : CoreComponent {

    val requestFactory: RequestFactory
}