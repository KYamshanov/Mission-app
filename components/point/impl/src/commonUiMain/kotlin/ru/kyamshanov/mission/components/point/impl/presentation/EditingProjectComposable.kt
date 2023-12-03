package ru.kyamshanov.mission.components.point.impl.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import dev.icerock.moko.resources.compose.painterResource
import ru.kyamshanov.mission.MissionTheme
import ru.kyamshanov.mission.components.points.api.common.TaskPriority
import ru.kyamshanov.mission.components.points.api.common.TaskSlim
import ru.kyamshanov.mission.components.points.api.common.TaskStatus
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

        Spacer(modifier = Modifier.height(8.dp))
        ComplexCell(modifier = Modifier.fillMaxWidth()) {
            val tasks = state.tasks
            if (tasks.isNotEmpty()) {
                item {
                    Column {
                        Text(
                            text = "Tasks",
                            style = MissionTheme.typography.field,
                            color = MissionTheme.colors.success,
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        for (task in tasks) {
                            ComposableItemText(task) { viewModel.openTask(task) }
                            Spacer(modifier = Modifier.height(2.dp))
                        }
                        Spacer(modifier = Modifier.height(5.dp))
                    }
                }
            }
            item {
                EditTextField(
                    modifier = Modifier.fillMaxWidth().padding(5.dp),
                    text = state.newTaskTitle,
                    label = "new task",
                    editable = true,
                    underlined = false,
                    onValueChange = { viewModel.setNexTaskTitle(it) },
                    rightIcon = {
                        Image(
                            modifier = Modifier
                                .clip(CircleShape)
                                .clickable { viewModel.createNewTask() }
                                .size(32.dp),
                            painter = painterResource(Res.images.ic_check_circle),
                            contentDescription = "done",
                            colorFilter = ColorFilter.tint(MissionTheme.colors.success)
                        )
                    }
                )
            }
        }
    }
}


@Composable
private fun ComposableItemText(
    item: TaskSlim,
    onClick: () -> Unit
) {
    val isHighPriority = item.priority == TaskPriority.PRIMARY
    val isLowPriority = item.priority == TaskPriority.LOW
    val isCompleted = item.status == TaskStatus.COMPLETED
    if (isHighPriority || isLowPriority) {
        Row {
            Text(
                modifier = Modifier.clickable { onClick() },
                text = item.title,
                style = MissionTheme.typography.titleSecondary
                    .run {
                        if (isCompleted) copy(textDecoration = TextDecoration.LineThrough)
                        else this
                    },
            )
            if (isHighPriority) {
                Image(
                    modifier = Modifier.fillMaxHeight(),
                    painter = painterResource(Res.images.ic_keyboard_double_arrow_up),
                    contentDescription = "high priority",
                    colorFilter = ColorFilter.tint(MissionTheme.colors.wrong)
                )
            } else {
                Image(
                    modifier = Modifier.fillMaxHeight(),
                    painter = painterResource(Res.images.ic_keyboard_double_arrow_down),
                    contentDescription = "low priority",
                    colorFilter = ColorFilter.tint(MissionTheme.colors.gray)
                )
            }
        }
    } else {
        Text(
            modifier = Modifier
                .clickable { onClick() },
            text = item.title,
            style = MissionTheme.typography.titleSecondary
                .run {
                    if (isCompleted) copy(textDecoration = TextDecoration.LineThrough)
                    else this
                },
        )
    }
}