package ru.kyamshanov.mission.core.network.impl.di

import ru.kyamshanov.mission.core.di.impl.koin.AbstractComponent
import ru.kyamshanov.mission.core.network.api.RequestFactory
import ru.kyamshanov.mission.core.network.api.di.NetworkComponent


internal class ModuleComponent : AbstractComponent(), NetworkComponent {
    override val requestFactory: RequestFactory = resolve()
}