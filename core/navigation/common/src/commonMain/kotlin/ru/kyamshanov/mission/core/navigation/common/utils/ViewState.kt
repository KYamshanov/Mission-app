package ru.kyamshanov.mission.core.navigation.common.utils

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.essenty.parcelable.Parcelable

inline fun <reified T : Parcelable> ComponentContext.viewState(initBlock: () -> T): MutableValue<T> {
    val key = T::class.simpleName.orEmpty()
    val mutableValue = MutableValue(stateKeeper.consume(key, T::class) ?: initBlock.invoke())
    stateKeeper.register(key) { mutableValue.value }
    return mutableValue
}

