package ru.kyamshanov.mission.authorization.impl.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalWindowInfo
import com.arkivanov.decompose.ComponentContext
import javafx.application.Platform
import javafx.concurrent.Worker
import javafx.embed.swing.JFXPanel
import javafx.scene.Scene
import javafx.scene.web.WebView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import ru.kyamshanov.mission.authorization.impl.presentation.composable.ComposeJFXPanel
import ru.kyamshanov.mission.core.di.impl.Di
import ru.kyamshanov.mission.core.navigation.common.ComposableScreen
import ru.kyamshanov.mission.core.platform_base.di.PlatformBaseComponent
import ru.kyamshanov.mission.core.platform_base.di.PlatformBaseComponentBuilder
import ru.kyamshanov.mission.oauth2.api.OAuth2Component
import netscape.javascript.JSObject
import ru.kyamshanov.mission.core.navigation.api.di.NavigationComponent
import ru.kyamshanov.mission.session_front.api.di.SessionFrontComponent
import ru.kyamshanov.mission.session_front.api.model.AccessData
import ru.kyamshanov.mission.session_front.api.model.Token
import java.awt.BorderLayout
import javax.swing.JPanel

class LoginScreen : ComposableScreen {

    @Composable
    override fun Content(componentContext: ComponentContext) {
        val visible = rememberSaveable { mutableStateOf(true) }
        val jfxPanel = remember { JFXPanel() }
        var jsObject = remember<JSObject?> { null }

        val window = requireNotNull(Di.getComponent<PlatformBaseComponent>()).mainComposeWindow
        val sessionFront = requireNotNull(Di.getComponent<SessionFrontComponent>()).sessionFront
        val navigator = requireNotNull(Di.getComponent<NavigationComponent>()).navigator

        Box(modifier = Modifier.fillMaxSize()) {

            if (visible.value)

                ComposeJFXPanel(
                    composeWindow = window,
                    jfxPanel = jfxPanel,
                    onCreate = {
                        Platform.runLater {
                            val root = WebView()

                            val engine = root.engine
                            val scene = Scene(root)
                            root.engine.loadWorker.stateProperty().addListener { _, _, newState ->
                                if (newState === Worker.State.SUCCEEDED) {
                                    jsObject = root.engine.executeScript("window") as JSObject
                                    // execute other javascript / setup js callbacks fields etc..
                                }
                            }
                            val interactor = requireNotNull(Di.getComponent<OAuth2Component>()).authorizationUriProvider
                            val codeVerifier = interactor.getCodeVerifier()
                            engine.locationProperty().addListener { obs, oldLocation, newLocation ->
                                println("obs")
                                println(oldLocation)
                                println(newLocation)
                                if (newLocation != null && newLocation.startsWith("http://127.0.0.1:8080/desktop/authorized")) {
                                    runBlocking {
                                        val code =
                                            newLocation.removePrefix("http://127.0.0.1:8080/desktop/authorized?code=")
                                        val token = interactor.getToken(code, codeVerifier)
                                        val accessData = AccessData(
                                            Token(token.accessToken), Token(token.refreshToken),
                                            emptyList()
                                        )
                                        sessionFront.openSession(accessData)
                                        withContext(Dispatchers.Main) { navigator.exit() }
                                    }
                                }
                            }
                            engine.loadWorker.exceptionProperty().addListener { _, _, newError ->
                                println("page load error : $newError")
                            }
                            jfxPanel.scene = scene
                            engine.load(interactor.provideAuthorizationUri(codeVerifier)) // can be a html document from resources ..
                            engine.setOnError { error -> println("onError : $error") }

                        }
                    }, onDestroy = {
                        Platform.runLater {
                            jsObject?.let { jsObj ->
                                // clean up code for more complex implementations i.e. removing javascript callbacks etc..
                            }
                        }
                    })
        }
    }
}