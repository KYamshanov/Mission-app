import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetbrains.lifecycle.LifecycleController
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.sun.java.accessibility.util.AWTEventMonitor
import dev.icerock.moko.resources.compose.painterResource
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.ktor.util.Identity.decode
import kotlinx.coroutines.*
import ru.kyamshanov.mission.MissionTheme
import ru.kyamshanov.mission.core.di.bundle.DiRegistry
import ru.kyamshanov.mission.core.di.impl.Di
import ru.kyamshanov.mission.core.navigation.MainContent
import ru.kyamshanov.mission.core.navigation.api.di.NavigationComponent
import ru.kyamshanov.mission.core.navigation.impl.DefaultRootComponent
import ru.kyamshanov.mission.core.navigation.impl.di.NavigationComponentImpl
import ru.kyamshanov.mission.core.navigation.runOnUiThread
import ru.kyamshanov.mission.core.platform_base.di.PlatformBaseComponentBuilder
import ru.kyamshanov.mission.core.ui.Res
import ru.kyamshanov.mission.foundation.api.splash_screen.di.SplashScreenComponent
import java.awt.Toolkit
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import javax.swing.UIManager


@OptIn(ExperimentalDecomposeApi::class)
fun main() = application {
    DiRegistry.registering()
    Napier.base(DebugAntilog())


    val lifecycle = LifecycleRegistry()

    val defaultRootComponent = runOnUiThread {
        DefaultRootComponent(
            initialScreen = requireNotNull(Di.getComponent<SplashScreenComponent>()).splashScreen,
            componentContext = DefaultComponentContext(lifecycle)
        )
    }

    requireNotNull(Di.getInternalComponent<NavigationComponent, NavigationComponentImpl>())
        .navigatorControllerHolder.rootComponent = defaultRootComponent

    val icon = painterResource(Res.images.app_icon)
    Toolkit.getDefaultToolkit().setDynamicLayout(false)
    Window(
        title = "Mission-app",
        state = rememberWindowState(width = 800.dp, height = 900.dp),
        onCloseRequest = { exitApplication() },
        icon = icon,
    ) {
        Di.registration(PlatformBaseComponentBuilder(window))
        val windowState = rememberWindowState()
        LifecycleController(lifecycle, windowState)
        MainContent(window, defaultRootComponent)
    }
}