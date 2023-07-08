package ru.kyamshanov.mission.foundation.impl.login.presentation.viewmodel

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.kyamshanov.mission.components.main_screen.api.domain.MainScreenLauncher
import ru.kyamshanov.mission.foundation.impl.login.domain.AuthenticationInteractor
import ru.kyamshanov.mission.foundation.impl.login.presentation.state.AuthenticationState

internal class AuthenticationViewModel(
    private val authenticationInteractor: AuthenticationInteractor,
    private val mainScreenLauncher: MainScreenLauncher,
) : ViewModel() {

    private val _state = MutableStateFlow(AuthenticationState())
    val state = _state.asStateFlow()

    fun setLogin(login: String) {
        authenticationInteractor.setLogin(login).onSuccess { updatedLogin ->
            _state.update { it.copy(login = updatedLogin) }
        }
    }

    fun setPassword(password: CharSequence) {
        authenticationInteractor.setPassword(password).onSuccess { updatedPassword ->
            _state.update { it.copy(password = updatedPassword) }
        }
    }

    fun clickOnLogin() = viewModelScope.launch {
        authenticationInteractor.onLogin()
            .onSuccess { mainScreenLauncher.launch() }
    }
}