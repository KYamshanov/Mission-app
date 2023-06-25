package ru.kyamshanov.mission.core.network.impl.di

import org.koin.core.module.Module
import ru.kyamshanov.mission.core.di.impl.koin.AbstractComponentBuilder
import ru.kyamshanov.mission.core.network.api.di.NetworkComponent

class NetworkComponentBuilder : AbstractComponentBuilder<NetworkComponent>() {

    override val modules: List<Module> = listOf(NetworkModule)

    override fun build(): NetworkComponent = ModuleComponent()
}