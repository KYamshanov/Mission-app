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
internal fun CreationTaskComposable(
    viewModel: CreationTaskViewModel
) {
    val state by viewModel.viewState.subscribeAsState()

    Surface(
        topContent = {
            TopBar(
                title = "Создание задачи",
                navigationListener = viewModel::onBack
            )
        },
        bottomContent = {
            Box(modifier = Modifier.fillMaxWidth()) {
                MainButton(modifier = Modifier.align(Alignment.TopEnd), label = "Создать", onClick = viewModel::create)
            }
        }
    ) {
        Column {
            CellInput(
                label = "Название",
                value = state.title,
                onValueChange = viewModel::setTitle,
                editable = true,
            )
            Spacer(modifier = Modifier.height(8.dp))
            CellInput(
                label = "Описание",
                value = state.description,
                onValueChange = viewModel::setDescription,
                editable = true,
                maxLines = 10,
            )
        }
    }
}