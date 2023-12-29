import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text


@Composable
internal fun WelcomeComposable(nextClock: () -> Unit) {
    Div {
        Text("Welcome to KYamshanov site")

        Button(
            attrs = {
                onClick { nextClock.invoke() }
            }
        ) { Text("Go next") }
    }
}