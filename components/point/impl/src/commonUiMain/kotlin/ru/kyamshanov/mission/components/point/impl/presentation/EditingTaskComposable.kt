package ru.kyamshanov.mission.components.point.impl.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import ru.kyamshanov.mission.core.ui.components.Surface
import ru.kyamshanov.mission.core.ui.components.TextFieldCompose
import ru.kyamshanov.mission.core.ui.components.TopBar

@Composable
internal fun EditingTaskComposable(
    viewModel: EditingTaskViewModel
) {
    val state by viewModel.taskState.subscribeAsState()

    if (state.isInitialized().not()) return
    Surface(
        topContent = {
            TopBar(
                title = state.title,
                navigationListener = { }
            )
        },
    ) {
        Column {
            TextFieldCompose(
                label = "Описание",
                text = state.description,
                onValueChange = {},
                editable = false,
            )
        }
    }
}