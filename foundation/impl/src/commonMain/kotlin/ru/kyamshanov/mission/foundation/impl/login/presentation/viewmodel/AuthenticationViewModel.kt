package ru.kyamshanov.mission.foundation.impl.login.presentation.viewmodel

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import ru.kyamshanov.mission.components.main_screen.api.domain.MainScreenLauncher
import ru.kyamshanov.mission.foundation.impl.login.domain.AuthenticationInteractor
import ru.kyamshanov.mission.foundation.impl.login.presentation.state.AuthenticationSideEffect
import ru.kyamshanov.mission.foundation.impl.login.presentation.state.AuthenticationState

internal class AuthenticationViewModel(
    private val authenticationInteractor: AuthenticationInteractor,
    private val mainScreenLauncher: MainScreenLauncher,
) : ViewModel(), ContainerHost<AuthenticationState, AuthenticationSideEffect> {

    override val container =
        viewModelScope.container<AuthenticationState, AuthenticationSideEffect>(AuthenticationState())

    fun setLogin(login: String) = intent {
        authenticationInteractor.setLogin(login).onSuccess { updatedLogin ->
            reduce { state.copy(login = updatedLogin) }
        }
    }

    fun setPassword(password: CharSequence) = intent {
        authenticationInteractor.setPassword(password).onSuccess { updatedPassword ->
            reduce { state.copy(password = updatedPassword) }
        }
    }

    fun clickOnLogin() = intent {
        authenticationInteractor.onLogin()
            .onSuccess { mainScreenLauncher.launch() }
    }
}