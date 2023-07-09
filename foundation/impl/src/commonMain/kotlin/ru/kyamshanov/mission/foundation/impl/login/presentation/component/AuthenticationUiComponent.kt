package ru.kyamshanov.mission.foundation.impl.login.presentation.component

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import ru.kyamshanov.mission.components.main_screen.api.domain.MainScreenLauncher
import ru.kyamshanov.mission.foundation.impl.login.domain.AuthenticationInteractor

class AuthenticationUiComponent(
    componentContext: ComponentContext,
    private val authenticationInteractor: AuthenticationInteractor,
    private val mainScreenLauncher: MainScreenLauncher
) : ComponentContext by componentContext {


    inner class ViewModel : InstanceKeeper.Instance {

        private val viewModelScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

        override fun onDestroy() {
            viewModelScope.cancel()
        }


        private val _loginState = mutableStateOf("")

        val loginState: State<String> = _loginState

        private val _passwordState = mutableStateOf<CharSequence>(String())


        val passwordState: State<CharSequence> = _passwordState


        fun setLogin(login: String) {
            _loginState.value = login
        }

        fun setPassword(password: CharSequence) {
            _passwordState.value = password
        }

        fun clickOnLogin() = viewModelScope.launch {
            authenticationInteractor.login(_loginState.value, _passwordState.value)
                .onSuccess { mainScreenLauncher.launch() }
        }
    }
}