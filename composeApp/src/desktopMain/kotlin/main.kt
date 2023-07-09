import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import ru.kyamshanov.mission.core.di.bundle.DiRegistry
import ru.kyamshanov.mission.core.di.impl.Di
import ru.kyamshanov.mission.core.navigation.impl.MainContent
import ru.kyamshanov.mission.core.platform_base.di.PlatformBaseComponentBuilder
import ru.kyamshanov.mission.foundation.api.splash_screen.di.SplashScreenComponent

fun main() = application {
    Di.registration(PlatformBaseComponentBuilder())
    DiRegistry.registering()
    Napier.base(DebugAntilog())

    val lifecycle = LifecycleRegistry()
    val componentContext = DefaultComponentContext(lifecycle)
    val splashScreen =
        requireNotNull(Di.getComponent<SplashScreenComponent>()).composableSplashScreen
    Window(
        title = "Mission-app",
        state = rememberWindowState(width = 800.dp, height = 600.dp),
        onCloseRequest = ::exitApplication,
    ) { MainContent(componentContext, splashScreen) }
}