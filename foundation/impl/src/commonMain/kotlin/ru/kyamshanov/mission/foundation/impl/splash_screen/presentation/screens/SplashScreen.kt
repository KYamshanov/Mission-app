package ru.kyamshanov.mission.foundation.impl.splash_screen.presentation.screens

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import ru.kyamshanov.mission.core.navigation.common.ComposableScreen
import ru.kyamshanov.mission.foundation.impl.splash_screen.presentation.component.SplashScreenDComponent
import ru.kyamshanov.mission.foundation.impl.splash_screen.presentation.composables.SplashComposable


@Parcelize
internal class SplashScreen : ComposableScreen, Parcelable {

    @Composable
    override fun Content(componentContext: ComponentContext) {
        SplashScreenDComponent(componentContext).viewModel

        SplashComposable()
    }
}


