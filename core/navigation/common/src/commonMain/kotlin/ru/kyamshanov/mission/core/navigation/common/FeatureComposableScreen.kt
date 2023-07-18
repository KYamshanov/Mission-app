package ru.kyamshanov.mission.core.navigation.common

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.parcelable.Parcelable
import ru.kyamshanov.mission.core.navigation.api.Screen
import kotlin.reflect.KClass

interface FeatureComposableScreen<FeatureComponent : Any> : Screen, Parcelable {

    val featureComponentClass: KClass<FeatureComponent>

    @Composable
    fun Content(componentContext: ComponentContext, featureComponent: FeatureComponent)
}