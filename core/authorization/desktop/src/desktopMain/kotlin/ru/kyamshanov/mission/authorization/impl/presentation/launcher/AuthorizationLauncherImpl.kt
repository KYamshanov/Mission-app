package ru.kyamshanov.mission.authorization.impl.presentation.launcher

import io.github.aakira.napier.Napier
import kotlinx.coroutines.*
import ru.kyamshanov.mission.authorization.api.AuthorizationLauncher
import ru.kyamshanov.mission.core.di.impl.Di
import ru.kyamshanov.mission.core.navigation.api.Navigator
import ru.kyamshanov.mission.oauth2.api.OAuth2Component
import ru.kyamshanov.mission.session_front.api.di.SessionFrontComponent
import ru.kyamshanov.mission.session_front.api.model.AccessData
import ru.kyamshanov.mission.session_front.api.model.Token
import java.awt.Desktop
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.URI


internal class AuthorizationLauncherImpl(
    private val navigator: Navigator
) : AuthorizationLauncher {

    private val authorizationUriProvider = requireNotNull(Di.getComponent<OAuth2Component>()).authorizationUriProvider
    private val sessionFront = requireNotNull(Di.getComponent<SessionFrontComponent>()).sessionFront


    override fun launch() {

        runBlocking {
            val codeVerifier = authorizationUriProvider.getCodeVerifier()

            val serverSocket: java.net.ServerSocket = java.net.ServerSocket(0)
            launch(Dispatchers.Default) {
                Napier.d("Local auth server started on port: ${serverSocket.getLocalPort()}")
                val socket = serverSocket.accept()
                val input = socket.getInputStream()
                val reader = BufferedReader(InputStreamReader(input))
                val line: String = reader.readLine()
                val authorizationCode = line.split("/desktop/authorized?code=")[1].split(" ")[0]
                val wtr = PrintWriter(socket.getOutputStream(), true)
                wtr.print(
                    """
                    HTTP/1.1 301 Moved Permanently
                    Location: https://github.com/KYamshanov
                    Content-Length: 0
                """.trimIndent()
                )
                wtr.flush()
                serverSocket.close()

                val token =
                    authorizationUriProvider.getToken(authorizationCode, codeVerifier, serverSocket.getLocalPort())
                val accessData = AccessData(Token(token.accessToken), Token(token.refreshToken), emptyList())
                sessionFront.openSession(accessData)
            }

            openBrowser(codeVerifier, serverSocket.getLocalPort())
        }
    }

    private fun openBrowser(codeVerifier: String, serverPort: Int) {
        Desktop.getDesktop().browse(URI(authorizationUriProvider.provideAuthorizationUri(codeVerifier, serverPort)))
    }
}