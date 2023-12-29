package ru.kyamshanov.mission.authorization.impl.presentation.composable

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.dom.Text
import ru.kyamshanov.mission.core.navigation.api.Navigator
import ru.kyamshanov.mission.oauth2.api.AuthenticationInteractor
import ru.kyamshanov.mission.session_front.api.SessionFront

@Composable
fun LoginComposable(
    authenticationInteractor: AuthenticationInteractor,
    sessionFront: SessionFront,
    navigator: Navigator,
) {
    Text("Not implement")
}