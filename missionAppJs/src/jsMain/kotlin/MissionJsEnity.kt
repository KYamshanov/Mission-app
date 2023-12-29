
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.jetbrains.compose.web.css.Style
import org.jetbrains.compose.web.dom.Div
import ru.kyamshanov.mission.TextStyles
import ru.kyamshanov.mission.core.di.bundle.DiRegistry
import ru.kyamshanov.mission.core.di.impl.Di
import ru.kyamshanov.mission.core.navigation.api.di.NavigationComponent
import ru.kyamshanov.mission.core.navigation.common.JsComposableScreen
import ru.kyamshanov.mission.core.navigation.impl.DefaultRootComponent
import ru.kyamshanov.mission.core.navigation.impl.di.NavigationComponentImpl
import ru.kyamshanov.mission.core.navigation.impl.domain.RootComponent
import ru.kyamshanov.mission.core.platform_base.di.PlatformBaseComponentBuilder
import ru.kyamshanov.mission.foundation.api.splash_screen.di.SplashScreenComponent

@Composable
fun MissionJsEntry() {
    Di.registration(PlatformBaseComponentBuilder())
    DiRegistry.registering()
    Napier.base(DebugAntilog())

    val componentContext = DefaultComponentContext(LifecycleRegistry())

    val defaultRootComponent = DefaultRootComponent(
        initialScreen = requireNotNull(Di.getComponent<SplashScreenComponent>()).splashScreen,
        componentContext = componentContext
    )

    requireNotNull(Di.getInternalComponent<NavigationComponent, NavigationComponentImpl>())
        .navigatorControllerHolder.rootComponent = defaultRootComponent

    Div {
        Style(TextStyles)

        val screenWithContextState =
            remember { mutableStateOf<RootComponent.ScreenWithContext?>(null) }

        defaultRootComponent.childStack.subscribe { stack ->
            screenWithContextState.value = stack.active.instance
        }

        val screen = screenWithContextState.value?.screen
        val context = screenWithContextState.value?.context

        when (screen) {
            is JsComposableScreen -> {
                screen.Content(requireNotNull(context) { " Context cannot be null for drawing screen " })
            }
        }
    }
}