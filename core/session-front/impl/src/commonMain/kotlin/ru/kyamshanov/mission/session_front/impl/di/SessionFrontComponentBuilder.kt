package ru.kyamshanov.mission.session_front.impl.di

import org.koin.core.module.Module
import ru.kyamshanov.mission.core.di.impl.koin.AbstractComponentBuilder
import ru.kyamshanov.mission.session_front.api.di.SessionFrontComponent

class SessionFrontComponentBuilder : AbstractComponentBuilder<SessionFrontComponent>() {

    override val modules: List<Module> = listOf(SessionFrontModule)
    override fun build(): SessionFrontComponent = ModuleComponent()
}