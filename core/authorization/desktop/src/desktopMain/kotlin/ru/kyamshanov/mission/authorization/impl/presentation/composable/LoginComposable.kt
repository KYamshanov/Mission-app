package ru.kyamshanov.mission.authorization.impl.presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.github.aakira.napier.Napier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.kyamshanov.mission.MissionTheme
import ru.kyamshanov.mission.core.navigation.api.Navigator
import ru.kyamshanov.mission.oauth2.api.AuthenticationInteractor
import ru.kyamshanov.mission.session_front.api.SessionFront
import ru.kyamshanov.mission.session_front.api.model.AccessData
import ru.kyamshanov.mission.session_front.api.model.Token
import java.awt.Desktop
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.URI

@Composable
fun LoginComposable(
    authenticationInteractor: AuthenticationInteractor,
    sessionFront: SessionFront,
    navigator: Navigator,
) {
    Box(modifier = Modifier.fillMaxSize().background(color = MissionTheme.colors.background)) {
        Text(modifier = Modifier.align(Alignment.Center), text = "Авторизация через браузер")

        LaunchedEffect(true) {

            val codeVerifier = authenticationInteractor.getCodeVerifier()
            val requestState = authenticationInteractor.getCodeVerifier()

            val serverSocket: java.net.ServerSocket = java.net.ServerSocket(0)
            launch(Dispatchers.Default) {
                Napier.d("Local auth server started on port: ${serverSocket.getLocalPort()}")
                val socket = serverSocket.accept()
                val input = socket.getInputStream()
                val reader = BufferedReader(InputStreamReader(input))
                val line: String = reader.readLine()

                val authorizationCode =
                    requireNotNull(authenticationInteractor.obtainAuthorizationCode(line)) { "authorizationCode required" }
                val state = requireNotNull(authenticationInteractor.obtainState(line)) { "obtainState required" }

                if (state != requestState) throw IllegalStateException("State is not matched")

                val wtr = PrintWriter(socket.getOutputStream())
                wtr.print(
                    """
                    HTTP/1.1 301 Moved Permanently
                    Location: https://github.com/KYamshanov
                    Content-Length: 0
                """.trimIndent()
                )
                wtr.flush()
                wtr.close()

                val token =
                    authenticationInteractor.getToken(authorizationCode, codeVerifier, serverSocket.getLocalPort())
                val accessData = AccessData(Token(token.accessToken), Token(token.refreshToken), emptyList())
                sessionFront.openSession(accessData)
                    .onSuccess {
                        withContext(Dispatchers.Main) { navigator.dismissAlert() }
                    }
                serverSocket.close()
            }

            openBrowser(
                authenticationInteractor.provideAuthorizationUri(
                    codeVerifier,
                    serverSocket.getLocalPort(),
                    requestState
                )
            )
        }
    }
}

private fun openBrowser(url: String) {
    Desktop.getDesktop().browse(URI(url))
}