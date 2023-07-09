package ru.kyamshanov.mission.foundation.impl.splash_screen.presentation.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import ru.kyamshanov.mission.components.main_screen.api.domain.MainScreenLauncher
import ru.kyamshanov.mission.foundation.api.login.domain.AuthenticationLauncher
import ru.kyamshanov.mission.session_front.api.SessionInfo
import ru.kyamshanov.mission.session_front.api.session.LoggedSession
import ru.kyamshanov.mission.session_front.api.session.LoggingSession
import ru.kyamshanov.mission.session_front.api.session.UnauthorizedSession
import ru.kyamshanov.mission.session_front.api.session.UnidentifiedSession

class SplashScreenDComponent(
    componentContext: ComponentContext,
    private val sessionInfo: SessionInfo,
    private val authenticationLauncher: AuthenticationLauncher,
    private val mainScreenLauncher: MainScreenLauncher
) : ComponentContext by componentContext {

    val viewModel = instanceKeeper.getOrCreate(::ViewModel)

    inner class ViewModel : InstanceKeeper.Instance {

        private val viewModelScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

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
            mainScreenLauncher.launch()
        }

        override fun onDestroy() {
            viewModelScope.cancel()
        }
    }
}