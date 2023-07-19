package ru.kyamshanov.mission.foundation.impl.login.presentation.screen

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.parcelable.Parcelize
import ru.kyamshanov.mission.core.navigation.api.Screen
import ru.kyamshanov.mission.core.navigation.common.ComposableScreen
import ru.kyamshanov.mission.core.navigation.common.utils.composeComponentContext
import ru.kyamshanov.mission.foundation.impl.login.presentation.component.AuthenticationUiComponent
import ru.kyamshanov.mission.foundation.impl.login.presentation.composable.AuthenticationComponent

@Parcelize
actual class AuthenticationComposableScreen : ComposableScreen {

    @Composable
    override fun Content(componentContext: ComponentContext) {
        val uiComponent = AuthenticationUiComponent(componentContext)
        AuthenticationComponent(viewModel = uiComponent.viewModel)
    }
}