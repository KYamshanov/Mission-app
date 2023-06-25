package ru.kyamshanov.mission.core.di.impl.koin

import ru.kyamshanov.mission.core.di.api.ComponentBuilder

class KoinComponentBuilder<T : AbstractComponent>(
    private val sourceComponentBuilder: ComponentBuilder<out T>
) : ComponentBuilder<KoinComponentImpl<T>> {
    override fun build() = KoinComponentImpl(sourceComponentBuilder.build())


}