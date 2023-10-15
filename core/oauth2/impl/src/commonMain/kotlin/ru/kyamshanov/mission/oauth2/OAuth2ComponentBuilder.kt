package ru.kyamshanov.mission.oauth2

import org.koin.core.module.Module
import ru.kyamshanov.mission.core.di.impl.koin.AbstractComponentBuilder
import ru.kyamshanov.mission.oauth2.api.OAuth2Component

class OAuth2ComponentBuilder : AbstractComponentBuilder<OAuth2Component>() {

    override val modules: List<Module> = listOf(OAuth2Module)
    override fun build(): OAuth2Component = ModuleComponent()
}