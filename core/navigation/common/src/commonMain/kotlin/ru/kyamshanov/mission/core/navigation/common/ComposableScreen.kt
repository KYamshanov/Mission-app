package ru.kyamshanov.mission.core.navigation.common

import androidx.compose.runtime.Composable
import com.arkivanov.essenty.parcelable.Parcelable
import ru.kyamshanov.mission.core.navigation.api.Screen

interface ComposableScreen : Screen, Parcelable {

    @Composable
    fun Content()
}