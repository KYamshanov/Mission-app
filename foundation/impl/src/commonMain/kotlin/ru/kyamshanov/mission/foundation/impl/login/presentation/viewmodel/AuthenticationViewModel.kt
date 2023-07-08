package ru.kyamshanov.mission.foundation.impl.login.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.launch
import ru.kyamshanov.mission.components.main_screen.api.domain.MainScreenLauncher
import ru.kyamshanov.mission.foundation.impl.login.domain.AuthenticationInteractor

internal class AuthenticationViewModel(
    private val authenticationInteractor: AuthenticationInteractor,
    private val mainScreenLauncher: MainScreenLauncher,
) : ViewModel() {

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