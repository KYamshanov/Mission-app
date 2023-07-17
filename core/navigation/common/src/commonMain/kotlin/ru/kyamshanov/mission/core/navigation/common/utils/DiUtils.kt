package ru.kyamshanov.mission.core.navigation.common.utils

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.lifecycle.doOnDestroy
import ru.kyamshanov.mission.core.di.impl.Di


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