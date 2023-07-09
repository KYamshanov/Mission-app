package ru.kyamshanov.mission.core.navigation.impl.domain

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import ru.kyamshanov.mission.core.navigation.api.Screen

@Parcelize
class ScreenConfig(val screen: Screen) : Parcelable