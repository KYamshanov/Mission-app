package ru.kyamshanov.mission.authorization.impl.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import ru.kyamshanov.mission.authorization.impl.presentation.composable.LoginComposable
import ru.kyamshanov.mission.core.di.impl.Di
import ru.kyamshanov.mission.core.navigation.common.ComposableScreen
import ru.kyamshanov.mission.oauth2.api.OAuth2Component
import ru.kyamshanov.mission.session_front.api.di.SessionFrontComponent

class LoginScreen : ComposableScreen {

    @Composable
    override fun Content(componentContext: ComponentContext) {
        val authorizationUriProvider = requireNotNull(Di.getComponent<OAuth2Component>()).authorizationUriProvider
        val sessionFront = requireNotNull(Di.getComponent<SessionFrontComponent>()).sessionFront

        LoginComposable(authorizationUriProvider, sessionFront)
    }
}