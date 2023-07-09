package ru.kyamshanov.mission.foundation.impl.splash_screen.presentation.screens

import androidx.compose.runtime.Composable
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import ru.kyamshanov.mission.components.main_screen.api.di.MainScreenComponent
import ru.kyamshanov.mission.core.di.impl.Di
import ru.kyamshanov.mission.core.navigation.common.ComposableScreen
import ru.kyamshanov.mission.core.ui.utils.viewModel
import ru.kyamshanov.mission.foundation.api.login.di.AuthenticationComponent
import ru.kyamshanov.mission.foundation.impl.splash_screen.presentation.composables.SplashComposable
import ru.kyamshanov.mission.foundation.impl.splash_screen.presentation.viewmodel.SplashViewModel
import ru.kyamshanov.mission.session_front.api.di.SessionFrontComponent


@Parcelize
internal class SplashScreen : ComposableScreen, Parcelable {

    @Composable
    override fun Content() {
        SplashComposable()

        val sessionComponent: SessionFrontComponent = requireNotNull(Di.getComponent())
        val authComponent: AuthenticationComponent = requireNotNull(Di.getComponent())
        val mainScreenComponent: MainScreenComponent = requireNotNull(Di.getComponent())

        viewModel {
            SplashViewModel(
                sessionInfo = sessionComponent.sessionInfo,
                authenticationLauncher = authComponent.launcher,
                mainScreenLauncher = mainScreenComponent.launcher
            )
        }
    }
}


