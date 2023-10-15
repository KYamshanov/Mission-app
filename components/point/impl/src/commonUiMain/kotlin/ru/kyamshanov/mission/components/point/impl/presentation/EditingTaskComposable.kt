package ru.kyamshanov.mission.components.point.impl.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import ru.kyamshanov.mission.core.ui.components.*

@Composable
internal fun EditingTaskComposable(
    viewModel: EditingTaskViewModel
) {
    val state by viewModel.viewState.subscribeAsState()

    if (state.isInitialized().not()) return
    Surface(
        topContent = {
            TopBar(
                title = state.title,
                navigationListener = viewModel::onBack
            )
        },
        bottomContent = {
            Box(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.align(Alignment.TopStart)) {
                    MainButton(
                        label = "today`s",
                        onClick = viewModel::todays
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    MainButton(
                        label = "week`s",
                        onClick = viewModel::weeks
                    )
                }
                AlternativeButton(
                    modifier = Modifier.align(Alignment.BottomEnd),
                    label = "Удалить",
                    onClick = viewModel::delete
                )
            }
        }
    ) {
        Column {
            TextFieldCompose(
                label = "Название",
                text = state.title,
                onValueChange = {},
                editable = false,
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextFieldCompose(
                label = "Описание",
                text = state.description,
                onValueChange = {},
                editable = false,
                maxLines = 15
            )
        }
    }
}