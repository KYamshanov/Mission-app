package ru.kyamshanov.mission.foundation.impl.splash_screen.presentation.viewmodel

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.launch
import ru.kyamshanov.mission.components.main_screen.api.domain.MainScreenLauncher
import ru.kyamshanov.mission.foundation.api.login.domain.AuthenticationLauncher
import ru.kyamshanov.mission.session_front.api.SessionInfo
import ru.kyamshanov.mission.session_front.api.session.LoggedSession
import ru.kyamshanov.mission.session_front.api.session.LoggingSession
import ru.kyamshanov.mission.session_front.api.session.UnauthorizedSession
import ru.kyamshanov.mission.session_front.api.session.UnidentifiedSession

internal class SplashViewModel(
    private val sessionInfo: SessionInfo,
    private val authenticationLauncher: AuthenticationLauncher,
    private val mainScreenLauncher: MainScreenLauncher,
) : ViewModel() {

    init {
        viewModelScope.launch {
            sessionInfo.sessionState.collect { session ->
                when (session) {
                    is LoggedSession -> openMainScreen()
                    is UnauthorizedSession -> startLogin()
                    UnidentifiedSession -> Unit
                    is LoggingSession -> Unit
                }
            }
        }
    }

    private fun startLogin() {
        authenticationLauncher.launch()
    }

    private fun openMainScreen() {
        authenticationLauncher.launch()
        mainScreenLauncher.launch()
    }
}