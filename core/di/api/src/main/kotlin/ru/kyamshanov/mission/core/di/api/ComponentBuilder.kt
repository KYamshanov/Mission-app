package ru.kyamshanov.mission.core.di.api

interface ComponentBuilder<T : Any> {

    fun build(): T
}