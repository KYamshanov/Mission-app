package ru.kyamshanov.mission.core.di.api

import kotlin.reflect.KClass

interface DiInterface<ComponentType : Any> {

    fun registration(clazz: KClass<out ComponentType>, builder: ComponentBuilder<out ComponentType>)
    fun getBuilder(clazz: KClass<out ComponentType>): ComponentBuilder<out ComponentType>?
    fun releaseComponent(clazz: KClass<out ComponentType>, holderId: Any)
    fun getComponent(clazz: KClass<out ComponentType>, holderId: Any? = null): ComponentType?
}
