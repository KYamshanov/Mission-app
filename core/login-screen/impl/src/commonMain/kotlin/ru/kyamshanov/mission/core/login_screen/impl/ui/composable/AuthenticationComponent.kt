package ru.kyamshanov.mission.core.login_screen.impl.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.kyamshanov.mission.MissionTheme
import ru.kyamshanov.mission.core.di.impl.Di
import ru.kyamshanov.mission.core.ui.components.CellInput
import ru.kyamshanov.mission.core.ui.components.MainButton
import ru.kyamshanov.mission.core.ui.extensions.imePadding
import ru.kyamshanov.mission.core.ui.extensions.systemBarsPadding
import ru.kyamshanov.mission.session_front.api.di.SessionFrontComponent

@Composable
internal fun AuthenticationComponent(
    modifier: Modifier = Modifier,
    onLogin: (String, CharSequence) -> Unit,
) {
    val loginState = rememberSaveable { mutableStateOf("") }
    val passwordState = rememberSaveable { mutableStateOf("") }
    val state = rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(state.value) {
        if (state.value) {
            requireNotNull(Di.getComponent<SessionFrontComponent>()).sessionFront.openSession(
                loginState.value,
                passwordState.value
            )

            state.value = false
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MissionTheme.colors.background),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = modifier
                .padding(30.dp)
                .systemBarsPadding()
                .imePadding()
        ) {
            CellInput(
                Modifier.fillMaxWidth(),
                value = loginState.value,
                onValueChange = { text -> loginState.value = text },
                label = "Логин",
            )
            Spacer(modifier = Modifier.height(16.dp))
            CellInput(
                Modifier.fillMaxWidth(),
                value = passwordState.value,
                onValueChange = { text -> passwordState.value = text },
                label = "Пароль",
                isMasked = true,
            )
            Spacer(modifier = Modifier.height(55.dp))
            MainButton(
                modifier = Modifier.fillMaxWidth().padding(10.dp),
                onClick = {
                    onLogin(loginState.value.trim(), passwordState.value.trim())
                    state.value = true
                },
                label = "Войти"
            )
        }
    }
}