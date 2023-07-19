package ru.kyamshanov.mission.core.navigation.common

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.parcelable.Parcelable
import ru.kyamshanov.mission.core.navigation.api.Screen

interface JsComposableScreen : Screen, Parcelable {

    @Composable
    fun Content(componentContext: ComponentContext)
}