package ru.kyamshanov.mission.authorization.impl.presentation.composable

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import kotlinx.coroutines.runBlocking
import ru.kyamshanov.mission.MissionTheme
import ru.kyamshanov.mission.core.navigation.api.Navigator
import ru.kyamshanov.mission.oauth2.api.AuthenticationInteractor
import ru.kyamshanov.mission.session_front.api.SessionFront
import ru.kyamshanov.mission.session_front.api.model.AccessData
import ru.kyamshanov.mission.session_front.api.model.Token

@Composable
fun LoginComposable(
    authenticationInteractor: AuthenticationInteractor,
    sessionFront: SessionFront,
    navigator: Navigator,
) {
    Box(modifier = Modifier.fillMaxSize().background(color = MissionTheme.colors.background)) {

        val codeVerifier = authenticationInteractor.getCodeVerifier()
        val requestState = authenticationInteractor.getCodeVerifier()

        val mUrl = authenticationInteractor.getAuthorizationUri(codeVerifier, state = requestState)

        println("URL1: ${mUrl}")
        // Adding a WebView inside AndroidView
        // with layout as full screen
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = {
                WebView(it).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    webViewClient = object : WebViewClient() {

                        override fun shouldOverrideUrlLoading(
                            view: WebView,
                            url: String?
                        ): Boolean {
                            if (url != null && url.startsWith("http://127.0.0.1:8080/desktop/authorized?code=")) {
                                runBlocking {

                                    val authorizationCode =
                                        checkNotNull(authenticationInteractor.obtainAuthorizationCode(url)) { "AuthorizationCode not found at response $url" }
                                    val state =
                                        checkNotNull(authenticationInteractor.obtainState(url)) { "State not found at response $url" }

                                    if (state != requestState) throw IllegalStateException("State is not matched")

                                    val token =
                                        authenticationInteractor.getToken(authorizationCode, codeVerifier)
                                    val accessData = AccessData(
                                        Token(token.accessToken), Token(token.refreshToken),
                                        emptyList()
                                    )
                                    sessionFront.openSession(accessData)
                                    navigator.dismissAlert()
                                }
                                return true
                            }
                            return false
                        }
                    }.apply { settings.javaScriptEnabled = true }
                    loadUrl(mUrl)
                }
            }, update = {
                it.loadUrl(mUrl)
            })
    }
}