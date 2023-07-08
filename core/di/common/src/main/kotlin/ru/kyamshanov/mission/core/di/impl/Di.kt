package ru.kyamshanov.mission.core.di.impl

import io.github.aakira.napier.Napier
import ru.kyamshanov.mission.core.di.api.ComponentBuilder
import ru.kyamshanov.mission.core.di.api.CoreComponent
import ru.kyamshanov.mission.core.di.impl.koin.AbstractComponent
import ru.kyamshanov.mission.core.di.impl.koin.AbstractComponentBuilder
import ru.kyamshanov.mission.core.di.impl.koin.KoinComponentBuilder
import java.io.Closeable
import kotlin.reflect.KClass

private const val CORE_COMPONENT_KEY = "core"


//TODO Сделать анализ DI, выглядит не очень
object Di {

    private val builders = mutableMapOf<KClass<*>, ComponentBuilder<*>>()
    private val componentsHolder = mutableMapOf<KClass<*>, MutableMap<Any, Any>>()

    @Suppress("UNCHECKED_CAST")
    inline fun <reified ComponentType : Any, ReturnType : ComponentType> getInternalComponent(holderId: Any? = null): ReturnType =
        getComponent(ComponentType::class, holderId) as ReturnType

    inline fun <reified T : Any> getComponent(holderId: Any? = null): T? =
        getComponent(T::class, holderId)

    inline fun <reified T : AbstractComponent> releaseComponent(holderId: Any) =
        releaseComponent(T::class, holderId)

    inline fun <reified T : Any> registration(builder: AbstractComponentBuilder<T>) =
        registration(T::class, builder)

    fun <T : Any> registration(clazz: KClass<T>, builder: AbstractComponentBuilder<T>) {
        this.builders[clazz] = KoinComponentBuilder(builder as AbstractComponentBuilder<AbstractComponent>)
        componentsHolder.remove(clazz)

        Napier.i("Registration component : ${clazz.simpleName}")
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> getBuilder(clazz: KClass<Any>): ComponentBuilder<T>? =
        this.builders[clazz] as? ComponentBuilder<T>

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> getComponent(clazz: KClass<T>, holderId: Any?): T? {
        var id = holderId

        if (id == null && CoreComponent::class.java.isAssignableFrom(clazz.java))
            id = CORE_COMPONENT_KEY

        return if (id != null) getSavableComponent(clazz, id)
        else (builders[clazz]?.build() as? T)?.also { onBeforeReleaseComponent(null, it) }
    }

    fun <T : Any> releaseComponent(clazz: KClass<T>, holderId: Any) {
        componentsHolder[clazz]?.let { components ->
            components.filter { it.key == holderId }.forEach(::onBeforeReleaseComponent)
            components.remove(holderId)
            if (components.isEmpty()) componentsHolder.remove(clazz)
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T : Any> getSavableComponent(clazz: KClass<T>, holderId: Any): T? {
        val holders = componentsHolder[clazz]
            ?: mutableMapOf<Any, Any>().also { componentsHolder[clazz] = it }
        return (holders[holderId]
            ?: builders[clazz]?.build()?.also { holders[holderId] = it }
            ?: releaseComponent(clazz, holderId)) as T?
    }

    private fun onBeforeReleaseComponent(id: Any?, component: Any) {
        if (component is Closeable) component.close()
    }
}