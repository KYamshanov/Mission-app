package ru.kyamshanov.mission.foundation.impl.login.presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.kyamshanov.mission.MissionTheme
import ru.kyamshanov.mission.core.di.impl.Di
import ru.kyamshanov.mission.core.ui.components.CellInput
import ru.kyamshanov.mission.core.ui.components.MainButton
import ru.kyamshanov.mission.core.ui.extensions.imePadding
import ru.kyamshanov.mission.core.ui.extensions.systemBarsPadding
import ru.kyamshanov.mission.core.ui.utils.collectAsState
import ru.kyamshanov.mission.core.ui.utils.viewModel
import ru.kyamshanov.mission.foundation.api.login.di.AuthenticationComponent
import ru.kyamshanov.mission.foundation.impl.login.di.ModuleComponent
import ru.kyamshanov.mission.foundation.impl.login.presentation.viewmodel.AuthenticationViewModel

@Composable
internal fun AuthenticationComponent(
    modifier: Modifier = Modifier,
    moduleComponent: ModuleComponent = requireNotNull(Di.getInternalComponent<AuthenticationComponent, ModuleComponent>()),
    viewModel: AuthenticationViewModel = viewModel { moduleComponent.viewModel }
) {

    val state by viewModel.collectAsState()

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
                value = state.login,
                onValueChange = { text -> viewModel.setLogin(text) },
                label = "Логин",
            )
            Spacer(modifier = Modifier.height(16.dp))
            CellInput(
                Modifier.fillMaxWidth(),
                value = state.password.toString(),
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