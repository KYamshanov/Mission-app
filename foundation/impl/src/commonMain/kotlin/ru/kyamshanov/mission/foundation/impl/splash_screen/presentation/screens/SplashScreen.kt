package ru.kyamshanov.mission.foundation.impl.splash_screen.presentation.screens

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import ru.kyamshanov.mission.components.main_screen.api.di.MainScreenComponent
import ru.kyamshanov.mission.core.di.impl.Di
import ru.kyamshanov.mission.core.navigation.common.ComposableScreen
import ru.kyamshanov.mission.core.navigation.common.utils.composeComponentContext
import ru.kyamshanov.mission.foundation.api.login.di.AuthenticationComponent
import ru.kyamshanov.mission.foundation.impl.splash_screen.presentation.component.SplashScreenDComponent
import ru.kyamshanov.mission.foundation.impl.splash_screen.presentation.composables.SplashComposable
import ru.kyamshanov.mission.session_front.api.di.SessionFrontComponent


@Parcelize
internal class SplashScreen : ComposableScreen, Parcelable {

    @Composable
    override fun Content(componentContext: ComponentContext) {
        SplashComposable()

        SplashScreenDComponent(
            componentContext = composeComponentContext(componentContext),
            sessionInfo = sessionComponent.sessionInfo
        ).viewModel
    }
}


