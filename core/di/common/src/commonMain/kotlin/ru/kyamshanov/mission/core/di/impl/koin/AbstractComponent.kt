package ru.kyamshanov.mission.core.di.impl.koin

import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.createScope
import org.koin.core.scope.Scope
import ru.kyamshanov.mission.core.di.api.CloseableComponent

abstract class AbstractComponent : KoinScopeComponent, CloseableComponent {

    override fun getKoin() = MissionKoinContext.koin

    override val scope: Scope by lazy { createScope(this) }

    inline fun <reified T : Any> resolve(): T = scope.get()

    override fun close() {
        scope.close()
    }
}

