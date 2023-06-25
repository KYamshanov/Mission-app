package ru.kyamshanov.mission.core.login_screen.impl.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.kyamshanov.mission.core.ui.components.CellInput
import ru.kyamshanov.mission.core.ui.components.MainButton

@Composable
internal fun AuthenticationComponent(
    modifier: Modifier = Modifier,
    onLogin: (String, CharSequence) -> Unit,
) {
    val loginState = rememberSaveable { mutableStateOf("") }
    val passwordState = rememberSaveable { mutableStateOf("") }

    Column(
        modifier = modifier
            .padding(30.dp)
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
            onClick = { onLogin(loginState.value.trim(), passwordState.value.trim()) },
            label = "Войти"
        )
    }
}