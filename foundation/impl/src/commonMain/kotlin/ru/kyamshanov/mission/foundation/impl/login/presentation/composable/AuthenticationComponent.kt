package ru.kyamshanov.mission.foundation.impl.login.presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.kyamshanov.mission.MissionTheme
import ru.kyamshanov.mission.core.ui.components.CellInput
import ru.kyamshanov.mission.core.ui.components.MainButton
import ru.kyamshanov.mission.core.ui.extensions.imePadding
import ru.kyamshanov.mission.core.ui.extensions.systemBarsPadding
import ru.kyamshanov.mission.foundation.impl.login.presentation.component.AuthenticationUiComponent

@Composable
internal fun AuthenticationComponent(
    modifier: Modifier = Modifier,
    viewModel: AuthenticationUiComponent.ViewModel
) {


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
                value = viewModel.loginState.value,
                onValueChange = { text -> viewModel.setLogin(text) },
                label = "Логин",
            )
            Spacer(modifier = Modifier.height(16.dp))
            CellInput(
                Modifier.fillMaxWidth(),
                value = viewModel.passwordState.value.toString(),
                onValueChange = { text -> viewModel.setPassword(text) },
                label = "Пароль",
                isMasked = true,
            )
            Spacer(modifier = Modifier.height(55.dp))
            MainButton(
                modifier = Modifier.fillMaxWidth().padding(10.dp),
                onClick = { viewModel.clickOnLogin() },
                label = "Войти"
            )
        }
    }
}