package ru.kyamshanov.mission.foundation.impl.splash_screen.presentation.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import ru.kyamshanov.mission.components.main_screen.api.di.MainScreenComponent
import ru.kyamshanov.mission.core.navigation.api.di.NavigationComponent
import ru.kyamshanov.mission.core.navigation.common.utils.ComponentDelegate
import ru.kyamshanov.mission.core.navigation.common.utils.di
import ru.kyamshanov.mission.core.navigation.common.utils.newRootScreen
import ru.kyamshanov.mission.core.navigation.common.utils.property
import ru.kyamshanov.mission.foundation.api.login.di.AuthenticationComponent
import ru.kyamshanov.mission.session_front.api.SessionInfo
import ru.kyamshanov.mission.session_front.api.di.SessionFrontComponent
import ru.kyamshanov.mission.session_front.api.session.LoggedSession
import ru.kyamshanov.mission.session_front.api.session.LoggingSession
import ru.kyamshanov.mission.session_front.api.session.UnauthorizedSession
import ru.kyamshanov.mission.session_front.api.session.UnidentifiedSession
import kotlin.reflect.KProperty

class SplashScreenDComponent(
    componentContext: ComponentContext,
) : ComponentContext by componentContext {

    private val sessionInfo: SessionInfo by di<SessionFrontComponent>()/*.property { sessionInfo }*/
    private val navigator by di<NavigationComponent>()

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
            navigator.newRootScreen<AuthenticationComponent>()
        }

        private fun openMainScreen() {
            navigator.newRootScreen<MainScreenComponent>()
        }

        override fun onDestroy() {
            viewModelScope.cancel()
        }
    }
}