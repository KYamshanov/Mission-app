package ru.kyamshanov.mission.foundation.impl.login.presentation.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import ru.kyamshanov.mission.components.main_screen.api.di.MainScreenComponent
import ru.kyamshanov.mission.core.navigation.api.di.NavigationComponent
import ru.kyamshanov.mission.core.navigation.common.utils.di
import ru.kyamshanov.mission.core.navigation.common.utils.diInternal
import ru.kyamshanov.mission.core.navigation.common.utils.newRootScreen
import ru.kyamshanov.mission.foundation.api.login.di.AuthenticationComponent
import ru.kyamshanov.mission.foundation.impl.login.di.ModuleComponent
import com.arkivanov.decompose.value.Value

class AuthenticationUiComponent(
    componentContext: ComponentContext
) : ComponentContext by componentContext {

    private val authenticationComponent =
        requireNotNull(diInternal<AuthenticationComponent, ModuleComponent>())
    private val navigator = requireNotNull(di<NavigationComponent>()).navigator

    val viewModel = instanceKeeper.getOrCreate(::ViewModel)

    inner class ViewModel : InstanceKeeper.Instance {

        private val viewModelScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

        override fun onDestroy() {
            viewModelScope.cancel()
        }


        private val _loginState = MutableValue("")

        val loginState: Value<String> = _loginState

        private val _passwordState = MutableValue<CharSequence>(String())


        val passwordState: Value<CharSequence> = _passwordState


        fun setLogin(login: String) {
            _loginState.value = login
        }

        fun setPassword(password: CharSequence) {
            _passwordState.value = password
        }

        fun clickOnLogin() = viewModelScope.launch {
            authenticationComponent.authenticationInteractor
                .login(_loginState.value, _passwordState.value)
                .onSuccess { navigator.newRootScreen<MainScreenComponent>() }
        }
    }
}