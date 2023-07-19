package ru.kyamshanov.mission.foundation.impl.splash_screen.presentation.screens

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.parcelable.Parcelize
import ru.kyamshanov.mission.core.navigation.api.Screen
import ru.kyamshanov.mission.core.navigation.common.ComposableScreen
import ru.kyamshanov.mission.foundation.impl.splash_screen.presentation.component.SplashScreenDComponent
import ru.kyamshanov.mission.foundation.impl.splash_screen.presentation.composables.SplashComposable


@Parcelize
internal actual class SplashScreen : Screen, ComposableScreen {

    @Composable
    override fun Content(componentContext: ComponentContext) {
        SplashScreenDComponent(componentContext).viewModel

        SplashComposable()
    }
}


