package ru.kyamshanov.mission.core.di.impl

import org.koin.core.Koin
import org.koin.core.annotation.KoinInternalApi
import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.createScope
import org.koin.core.definition.BeanDefinition
import org.koin.core.definition.Kind
import org.koin.core.instance.ScopedInstanceFactory
import org.koin.core.module.Module
import org.koin.core.scope.Scope
import org.koin.dsl.module
import ru.kyamshanov.mission.core.di.impl.koin.AbstractComponent
import ru.kyamshanov.mission.core.di.impl.koin.MissionKoinContext
import kotlin.reflect.KClass

fun <T : Any> buildObject(
    vararg components: KClass<out Any>,
    factory: (Scope) -> T
): Pair<T, () -> Unit> {
    val holderId = Any()
    val module = module {
        scope<InternalScope> {
            components.map {
                checkNotNull(
                    Di.getComponent(
                        clazz = it,
                        holderId = holderId
                    ) as? AbstractComponent
                ) { "Component ${it.simpleName} failed" }
            }.forEach { component ->
                val def = ScopedInstanceFactory(
                    beanDefinition = BeanDefinition(
                        scopeQualifier = scopeQualifier,
                        primaryType = component::class,
                        kind = Kind.Scoped,
                        definition = { component }
                    )
                )
                @OptIn(KoinInternalApi::class)
                this@module.indexPrimaryType(def)
            }
        }
    }

    val scope = InternalScope(module)

    components.map {
        checkNotNull(
            Di.getComponent(
                clazz = it,
                holderId = holderId
            ) as? AbstractComponent
        ) { "Component ${it.simpleName} failed" }
    }.forEach { component ->
        val b = scope.scope.get<KoinScopeComponent>(clazz = component::class)
        scope.scope.linkTo(b.scope)
    }

    return factory(scope.scope) to {
        components.forEach { Di.releaseComponent(it, holderId) }
        scope.scope.close()
    }
}

private class InternalScope(module: Module) : KoinScopeComponent {

    init {
        MissionKoinContext.koin.loadModules(listOf(module))
    }

    override fun getKoin(): Koin = MissionKoinContext.koin
    override val scope: Scope by lazy { createScope(this) }
}