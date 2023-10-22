package ru.kyamshanov.mission.authorization.impl.presentation.screen

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import kotlinx.parcelize.Parcelize
import ru.kyamshanov.mission.authorization.impl.presentation.composable.LoginComposable
import ru.kyamshanov.mission.core.di.impl.Di
import ru.kyamshanov.mission.core.navigation.api.di.NavigationComponent
import ru.kyamshanov.mission.core.navigation.common.ComposableScreen
import ru.kyamshanov.mission.oauth2.api.OAuth2Component
import ru.kyamshanov.mission.session_front.api.di.SessionFrontComponent

@Parcelize
class LoginScreen : ComposableScreen {

    @Composable
    override fun Content(componentContext: ComponentContext) {
        val authorizationUriProvider =
            requireNotNull(Di.getComponent<OAuth2Component>()).authorizationUriProvider
        val sessionFront = requireNotNull(Di.getComponent<SessionFrontComponent>()).sessionFront
        val navComponent = requireNotNull(Di.getComponent<NavigationComponent>())

        LoginComposable(authorizationUriProvider, sessionFront, navComponent.navigator)
    }
}