package ru.kyamshanov.mission.core.navigation.common.utils

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.Lifecycle
import ru.kyamshanov.mission.core.di.impl.Di
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

inline fun <reified ComponentType : Any> ComponentContext.di(): ComponentDelegate<ComponentType> =
    ComponentType::class.let { di(it, it) }

inline fun <reified ComponentType : Any, reified InternalComponentType : ComponentType> ComponentContext.diInternal(): ComponentDelegate<InternalComponentType> =
    di(ComponentType::class, InternalComponentType::class)

fun <ComponentType : Any, ReturnType : ComponentType> ComponentContext.di(
    componentClass: KClass<ComponentType>,
    returnClass: KClass<ReturnType>
): ComponentDelegate<ReturnType> =
    ComponentDelegateImpl(this, componentClass, returnClass)

interface ComponentDelegate<ComponentType : Any> {

    operator fun getValue(thisRef: Any?, property: KProperty<*>): ComponentType?

    operator fun getValue(thisRef: Any, property: KProperty<*>): ComponentType
}

fun <ComponentType : Any, ReturnType : Any> ComponentDelegate<ComponentType>.property(obtainLogic: ComponentType.() -> ReturnType): ComponentDelegate<ReturnType> =
    PropertyComponentDelegate(obtainLogic, this)

private class PropertyComponentDelegate<ComponentType : Any, ReturnType : Any>(
    private val obtainLogic: (ComponentType) -> ReturnType,
    private val componentDelegate: ComponentDelegate<ComponentType>
) : ComponentDelegate<ReturnType> {

    override fun getValue(thisRef: Any?, property: KProperty<*>): ReturnType? =
        componentDelegate.getValue(thisRef, property)?.let(obtainLogic)

    override fun getValue(thisRef: Any, property: KProperty<*>): ReturnType =
        componentDelegate.getValue(thisRef, property).let(obtainLogic)
}

private class ComponentDelegateImpl<ComponentType : Any, ReturnType : ComponentType>(
    private val componentContext: ComponentContext,
    private val componentClass: KClass<ComponentType>,
    private val returnClass: KClass<ReturnType>
) : ComponentDelegate<ReturnType> {

    private var component: ReturnType? = null

    init {
        componentContext.lifecycle.subscribe(object : Lifecycle.Callbacks {
            override fun onCreate() {
                component = Di.getInternalComponent(componentClass, returnClass, componentContext)
            }

            override fun onDestroy() {
                Di.releaseComponent(componentClass, componentContext)
                component = null
            }
        })
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): ReturnType? = component
    override fun getValue(thisRef: Any, property: KProperty<*>): ReturnType =
        requireNotNull(component)
}