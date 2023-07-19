package ru.kyamshanov.mission.core.di.impl.koin

import org.koin.core.component.*
import org.koin.core.module.Module
import org.koin.core.scope.Scope
//import java.io.Closeable

abstract class AbstractComponent : KoinScopeComponent/*, Closeable */{

    override fun getKoin() = MissionKoinContext.koin

    override val scope: Scope by lazy { createScope(this) }

    inline fun <reified T : Any> AbstractComponent.resolve(): T = scope.get<T>()

/*    override fun close() {
        scope.close()
    }*/
}

