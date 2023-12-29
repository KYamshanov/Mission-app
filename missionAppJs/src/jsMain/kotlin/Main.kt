import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import kotlinx.coroutines.InternalCoroutinesApi
import org.jetbrains.compose.web.renderComposable


@InternalCoroutinesApi
fun main() {
    renderComposable(rootElementId = "root") {
        val missionVisible = remember { mutableStateOf(false) }
        if (missionVisible.value) {
            MissionJsEntry()
        } else {
            WelcomeComposable { missionVisible.value = true }
        }
    }
}

