package ru.kyamshanov.mission.components.point.impl.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import dev.icerock.moko.resources.compose.painterResource
import ru.kyamshanov.mission.MissionTheme
import ru.kyamshanov.mission.components.points.api.common.TaskPriority
import ru.kyamshanov.mission.components.points.api.common.TaskStatus
import ru.kyamshanov.mission.components.points.api.common.TaskType
import ru.kyamshanov.mission.core.ui.Res
import ru.kyamshanov.mission.core.ui.components.*

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun EditingProjectComposable(
    viewModel: EditingProjectViewModel
) {
    val state: EditingProjectViewModel.State by viewModel.viewState.subscribeAsState()

    if (state.isInitialized().not()) return
    Surface(
        topContent = {
            TopBar(
                title = "",
                navigationListener = viewModel::onBack,
                trailingContent = {
                    Image(
                        modifier = Modifier.padding(10.dp)
                            .clip(CircleShape)
                            .clickable { viewModel.delete() }
                            .size(32.dp),
                        painter = painterResource(Res.images.ic_delete_forever),
                        contentDescription = "delete",
                        colorFilter = ColorFilter.tint(MissionTheme.colors.primary)
                    )
                }
            )
        },
    ) {

        Cell(modifier = Modifier.fillMaxWidth()) {
            TextFieldCompose(
                modifier = Modifier.fillMaxWidth(),
                label = "Название",
                text = state.title,
                onValueChange = { viewModel.setTitle(it) },
                editable = state.isTitleEditing != null,
                underlined = state.description != null,
                onEditingStarted = viewModel::startEditingTitle,
                editing = state.isTitleEditing == true
            )


            if (state.description != null) {
                Spacer(modifier = Modifier.height(8.dp))
                TextFieldCompose(
                    modifier = Modifier.fillMaxWidth(),
                    label = "Описание",
                    text = state.description ?: "",
                    onValueChange = { viewModel.setDescription(it) },
                    maxLines = 15,
                    underlined = false,
                    editable = state.isDescriptionEditing != null,
                    onEditingStarted = viewModel::startEditingDescription,
                    editing = state.isDescriptionEditing == true
                )
            }
        }

        if (state.saveChangesButtonAvailable) {
            AlternativeButton(
                content = {
                    Text(
                        text = "Сохранить изменения",
                        style = MissionTheme.typography.alternativeButtonStyle + MissionTheme.typography.medium
                    )
                },
                onClick = viewModel::saveChanges,
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        Row {
            for (label in state.labels) {
                Spacer(modifier = Modifier.height(8.dp))
                LabelButton(label = label.title, color = label.color) { }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}