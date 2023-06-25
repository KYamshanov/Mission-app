package ru.kyamshanov.mission.core.di.impl

import ru.kyamshanov.mission.core.di.api.ComponentBuilder
import ru.kyamshanov.mission.core.di.api.CoreComponent
import ru.kyamshanov.mission.core.di.api.DiInterface
import ru.kyamshanov.mission.core.di.impl.koin.AbstractComponent
import ru.kyamshanov.mission.core.di.impl.koin.KoinComponentBuilder
import kotlin.reflect.KClass

private const val CORE_COMPONENT_KEY = "core"

object Di : DiInterface<AbstractComponent> {

    private val builders = mutableMapOf<KClass<*>, KoinComponentBuilder<*>>()
    private val componentsHolder = mutableMapOf<KClass<*>, MutableMap<Any, Any>>()

    @Suppress("UNCHECKED_CAST")
    inline fun <reified ComponentType : AbstractComponent, ReturnType : ComponentType> getInternalComponent(holderId: Any? = null): ReturnType =
        getComponent(ComponentType::class, holderId) as ReturnType

    inline fun <reified T : AbstractComponent> getComponent(holderId: Any? = null) =
        getComponent(T::class, holderId)

    inline fun <reified T : AbstractComponent> releaseComponent(holderId: Any) =
        releaseComponent(T::class, holderId)

    inline fun <reified T : AbstractComponent> registration(builder: ComponentBuilder<T>) =
        registration(T::class, builder)

    override fun registration(clazz: KClass<out AbstractComponent>, builder: ComponentBuilder<out AbstractComponent>) {
        this.builders[clazz] = KoinComponentBuilder(builder)
        componentsHolder.remove(clazz)

        println("Registration component : ${clazz.simpleName}")
    }

    @Suppress("UNCHECKED_CAST")
    override fun getBuilder(clazz: KClass<out AbstractComponent>): ComponentBuilder<out AbstractComponent>? =
        this.builders[clazz] as? ComponentBuilder<AbstractComponent>

    @Suppress("UNCHECKED_CAST")
    override fun getComponent(clazz: KClass<out AbstractComponent>, holderId: Any?): AbstractComponent? {
        var id = holderId

        if (id == null && CoreComponent::class.java.isAssignableFrom(clazz.java))
            id = CORE_COMPONENT_KEY

        return if (id != null) getSavableComponent(clazz, id)
        else builders[clazz]?.build() as? AbstractComponent
    }

    override fun releaseComponent(clazz: KClass<out AbstractComponent>, holderId: Any) {
        componentsHolder[clazz]?.let {
            it.remove(holderId)
            if (it.isEmpty()) componentsHolder.remove(clazz)
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun getSavableComponent(clazz: KClass<out AbstractComponent>, holderId: Any): AbstractComponent? {
        val holders = componentsHolder[clazz]
            ?: mutableMapOf<Any, Any>().also { componentsHolder[clazz] = it }
        return (holders[holderId]
            ?: builders[clazz]?.build()?.also { holders[holderId] = it }
            ?: releaseComponent(clazz, holderId)) as AbstractComponent?
    }
}