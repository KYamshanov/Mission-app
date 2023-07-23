package ru.kyamshanov.mission.core.navigation.common.utils

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.lifecycle.doOnDestroy
import ru.kyamshanov.mission.core.di.impl.Di
import kotlin.reflect.KClass
import kotlin.reflect.KProperty


inline fun <reified ComponentType : Any> ComponentContext.di(): ComponentType? {
    if (lifecycle.state == Lifecycle.State.DESTROYED) return null
    return Di.getComponent<ComponentType>(this).also {
        lifecycle.doOnDestroy { Di.releaseComponent<ComponentType>(this) }
    }
}

inline fun <reified ComponentType : Any, reified InternalComponentType : ComponentType> ComponentContext.diInternal(): InternalComponentType? {
    if (lifecycle.state == Lifecycle.State.DESTROYED) return null
    return Di.getInternalComponent<ComponentType, InternalComponentType>(this).also {
        lifecycle.doOnDestroy { Di.releaseComponent<ComponentType>(this) }
    }
}

inline operator fun <reified ComponentType : Any> DestructibleInstanceKeeper<ComponentType>.getValue(
    thisRef: Any?,
    property: KProperty<*>
): ComponentType = get()


inline fun <reified ComponentType : Any> InstanceKeeper.di(): DestructibleInstanceKeeper<ComponentType>? =
    this.di(componentClass = ComponentType::class)

inline fun <reified ComponentType : Any, reified InternalComponentType : ComponentType> InstanceKeeper.diInternal(): DestructibleInstanceKeeper<InternalComponentType>? =
    this.diInternal(
        componentClass = ComponentType::class,
        internalComponentClass = InternalComponentType::class
    )


interface DestructibleInstanceKeeper<InstanceType : Any> : InstanceKeeper.Instance {

    fun get(): InstanceType
}

fun <ComponentType : Any> InstanceKeeper.di(componentClass: KClass<ComponentType>): DestructibleInstanceKeeper<ComponentType>? =
    Di.getComponent(componentClass, this)?.let { component ->
        DestructibleInstanceKeeperImpl(component) { Di.releaseComponent(componentClass, this) }
    }

fun <ComponentType : Any, InternalComponentType : ComponentType> InstanceKeeper.diInternal(
    componentClass: KClass<ComponentType>,
    internalComponentClass: KClass<InternalComponentType>
): DestructibleInstanceKeeper<InternalComponentType>? =
    Di.getInternalComponent(componentClass, internalComponentClass, this)?.let { component ->
        DestructibleInstanceKeeperImpl(component) { Di.releaseComponent(componentClass, this) }
    }

private class DestructibleInstanceKeeperImpl<InstanceType : Any>(
    instance: InstanceType,
    private val onDestroy: (InstanceType) -> Unit
) : DestructibleInstanceKeeper<InstanceType> {

    private var mInstance: InstanceType? = instance

    override fun get(): InstanceType =
        mInstance ?: throw IllegalStateException("Instance has been destroyed ")

    override fun onDestroy() {
        onDestroy.invoke(get())
        mInstance = null
    }
}
