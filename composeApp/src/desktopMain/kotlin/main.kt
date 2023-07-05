import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import ru.kyamshanov.mission.core.di.bundle.DiRegistry
import ru.kyamshanov.mission.navigation.MainContent

fun main() = application {
    DiRegistry.registering()
    Window(
        title = "Mission-app",
        state = rememberWindowState(width = 800.dp, height = 600.dp),
        onCloseRequest = ::exitApplication,
    ) { MainContent() }
}