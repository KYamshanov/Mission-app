import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.round
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetbrains.lifecycle.LifecycleController
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.sun.javafx.application.PlatformImpl
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.ktor.http.*
import io.ktor.util.*
import javafx.application.Platform
import javafx.concurrent.Worker
import javafx.embed.swing.JFXPanel
import javafx.scene.Scene
import javafx.scene.web.WebView
import netscape.javascript.JSObject
import ru.kyamshanov.mission.core.di.bundle.DiRegistry
import ru.kyamshanov.mission.core.di.impl.Di
import ru.kyamshanov.mission.core.navigation.MainContent
import ru.kyamshanov.mission.core.navigation.api.di.NavigationComponent
import ru.kyamshanov.mission.core.navigation.impl.DefaultRootComponent
import ru.kyamshanov.mission.core.navigation.impl.di.NavigationComponentImpl
import ru.kyamshanov.mission.core.platform_base.di.PlatformBaseComponentBuilder
import ru.kyamshanov.mission.foundation.api.splash_screen.di.SplashScreenComponent
import java.awt.BorderLayout
import javax.swing.JPanel

@OptIn(ExperimentalDecomposeApi::class)
fun main() = application {
    Di.registration(PlatformBaseComponentBuilder())
    DiRegistry.registering()
    Napier.base(DebugAntilog())


    val lifecycle = LifecycleRegistry()
    val componentContext = DefaultComponentContext(LifecycleRegistry())

    val defaultRootComponent = DefaultRootComponent(
        initialScreen = requireNotNull(Di.getComponent<SplashScreenComponent>()).splashScreen,
        componentContext = componentContext
    )

    requireNotNull(Di.getInternalComponent<NavigationComponent, NavigationComponentImpl>())
        .navigatorControllerHolder.rootComponent = defaultRootComponent

    val finishListener = object : PlatformImpl.FinishListener {
        override fun idle(implicitExit: Boolean) {}
        override fun exitCalled() {}
    }
    PlatformImpl.addListener(finishListener)


    Window(
        title = "Mission-app",
        state = rememberWindowState(width = 800.dp, height = 600.dp),
        onCloseRequest = {
            PlatformImpl.removeListener(finishListener)
            exitApplication()
        },
    ) {

        val visible = rememberSaveable { mutableStateOf(true) }
        val jfxPanel = remember { JFXPanel() }
        var jsObject = remember<JSObject?> { null }

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
                        engine.locationProperty().addListener { obs, oldLocation, newLocation ->
                            println("obs")
                            println(oldLocation)
                            println(newLocation)
                            if (newLocation != null && newLocation.startsWith("http://127.0.0.1:8080/desktop/authorized")) {
                                visible.value = false
                            }
                        }
                        engine.loadWorker.exceptionProperty().addListener { _, _, newError ->
                            println("page load error : $newError")
                        }
                        jfxPanel.scene = scene
                        engine.load("http://localhost:9000/oauth2/authorize?response_type=code&client_id=desktop-client&scope=openid&redirect_uri=http://127.0.0.1:8080/desktop/authorized&code_challenge=MzY1QzQ4REYxMTBGQjY1NDE4RTA0NTNBQTA3OUM3NzUwQUJEOTg5QURFREFCQUQ5MzUyNThERkNBRThERkNFRA==&code_challenge_method=S256") // can be a html document from resources ..
                        engine.setOnError { error -> println("onError : $error") }

                    }
                }, onDestroy = {
                    Platform.runLater {
                        jsObject?.let { jsObj ->
                            // clean up code for more complex implementations i.e. removing javascript callbacks etc..
                        }
                    }
                })

        val windowState = rememberWindowState()
        LifecycleController(lifecycle, windowState)

        MainContent(defaultRootComponent)
    }
}


@Composable
fun ComposeJFXPanel(
    composeWindow: ComposeWindow,
    jfxPanel: JFXPanel,
    onCreate: () -> Unit,
    onDestroy: () -> Unit = {}
) {
    val jPanel = remember { JPanel() }
    val density = LocalDensity.current.density

    Layout(
        content = {},
        modifier = Modifier.onGloballyPositioned { childCoordinates ->
            val coordinates = childCoordinates.parentCoordinates!!
            val location = coordinates.localToWindow(Offset.Zero).round()
            val size = coordinates.size
            jPanel.setBounds(
                (location.x / density).toInt(),
                (location.y / density).toInt(),
                (size.width / density).toInt(),
                (size.height / density).toInt()
            )
            jPanel.validate()
            jPanel.repaint()
        },
        measurePolicy = { _, _ -> layout(0, 0) {} })

    DisposableEffect(jPanel) {
        composeWindow.add(jPanel)
        jPanel.layout = BorderLayout(0, 0)
        jPanel.add(jfxPanel)
        onCreate()
        onDispose {
            onDestroy()
            composeWindow.remove(jPanel)
        }
    }
}