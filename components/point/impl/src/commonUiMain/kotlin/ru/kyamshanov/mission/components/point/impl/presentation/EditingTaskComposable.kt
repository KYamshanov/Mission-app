package ru.kyamshanov.mission.components.point.impl.presentation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import dev.icerock.moko.resources.compose.painterResource
import ru.kyamshanov.mission.MissionTheme
import ru.kyamshanov.mission.components.points.api.common.LabelModel
import ru.kyamshanov.mission.components.points.api.common.TaskPriority
import ru.kyamshanov.mission.components.points.api.common.TaskStatus
import ru.kyamshanov.mission.components.points.api.common.TaskType
import ru.kyamshanov.mission.core.ui.Res
import ru.kyamshanov.mission.core.ui.components.*

@OptIn(ExperimentalLayoutApi::class, ExperimentalFoundationApi::class)
@Composable
internal fun EditingTaskComposable(
    viewModel: EditingTaskViewModel
) {
    val state: EditingTaskViewModel.State by viewModel.viewState.subscribeAsState()

    if (state.isInitialized().not()) return
    Surface(
        topContent = {
            TopBar(
                title = state.toolBarTitle,
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
        bottomContent = {
            Box(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.align(Alignment.CenterStart)) {
                    MainButton(
                        label = "today`s",
                        isSoftStyle = state.type != TaskType.TODAYS,
                        onClick = viewModel::todays
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    MainButton(
                        label = "week`s",
                        isSoftStyle = state.type != TaskType.WEEKS,
                        onClick = viewModel::weeks
                    )
                }

                Column(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (state.status == TaskStatus.CREATED) {
                        Image(
                            modifier = Modifier
                                .clip(CircleShape)
                                .clickable { viewModel.completed() }
                                .size(64.dp),
                            painter = painterResource(Res.images.ic_check_circle),
                            contentDescription = "done",
                            colorFilter = ColorFilter.tint(MissionTheme.colors.success)
                        )
                        Text(
                            "Готово",
                            style = MissionTheme.typography.inputText.copy(color = MissionTheme.colors.success)
                        )
                    } else {
                        Image(
                            modifier = Modifier
                                .clip(CircleShape)
                                .clickable { viewModel.resetStatus() }
                                .size(64.dp),
                            painter = painterResource(Res.images.ic_settings_backup_restore),
                            contentDescription = "reset",
                            colorFilter = ColorFilter.tint(MissionTheme.colors.gray)
                        )
                        Text(
                            "Восстановить",
                            style = MissionTheme.typography.inputText.copy(color = MissionTheme.colors.gray)
                        )
                    }
                }

                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        modifier = Modifier
                            .clip(CircleShape)
                            .clickable { viewModel.priority() }
                            .size(64.dp),
                        painter = painterResource(Res.images.ic_keyboard_double_arrow_up),
                        contentDescription = "to priority",
                        colorFilter = ColorFilter.tint(
                            color = if (state.priority == TaskPriority.PRIMARY) MissionTheme.colors.gold else MissionTheme.colors.gray
                        )
                    )
                    Text(
                        text = when (state.priority) {
                            TaskPriority.PRIMARY -> "high"
                            TaskPriority.LOW -> "low"
                            null -> "medium"
                        },
                        textAlign = TextAlign.Justify,
                        style = MissionTheme.typography.inputHint.copy(color = if (state.priority == null) MissionTheme.colors.gray else MissionTheme.colors.gold)
                    )
                    Image(
                        modifier = Modifier
                            .clip(CircleShape)
                            .clickable { viewModel.low() }
                            .size(64.dp),
                        painter = painterResource(Res.images.ic_keyboard_double_arrow_down),
                        contentDescription = "medium",
                        colorFilter = ColorFilter.tint(
                            color = if (state.priority == TaskPriority.LOW) MissionTheme.colors.gold else MissionTheme.colors.gray
                        )
                    )
                }
            }
        }
    ) {
        Cell(modifier = Modifier.fillMaxWidth()) {
            TextFieldCompose(
                modifier = Modifier.fillMaxWidth(),
                label = "Название",
                text = state.title,
                onValueChange = { viewModel.setTitle(it) },
                editable = state.isTitleEditing != null,
                underlined = state.descriptionVisible,
                onEditingStarted = viewModel::startEditingTitle,
                editing = state.isTitleEditing == true
            )


            if (state.descriptionVisible) {
                Spacer(modifier = Modifier.height(8.dp))
                TextFieldCompose(
                    modifier = Modifier.fillMaxWidth(),
                    label = "Описание",
                    text = state.description,
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
        Spacer(modifier = Modifier.height(5.dp))
        Row {
            Text(
                text = "Метки:",
                style = MissionTheme.typography.titleSecondary
            )

            FlowRow(
                modifier = Modifier
                    .clickable(enabled = state.isSettingLabelsAvailable) { viewModel.startEditingLabels() },
                verticalArrangement = Arrangement.Center
            ) {
                if (state.selectedLabels.isEmpty()) {
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = "Нажните чтобы настроить",
                        style = MissionTheme.typography.inputText
                    )
                } else {
                    for (model in state.selectedLabels) {
                        Spacer(modifier = Modifier.width(2.dp))
                        LabelButton(modifier = Modifier.padding(3.dp), label = model.title, color = model.color)
                    }
                }

                Spacer(modifier = Modifier.width(2.dp))
                Box {
                    Image(
                        painter = painterResource(Res.images.ic_settings),
                        contentDescription = "settings",
                        modifier = Modifier.size(24.dp),
                        colorFilter = ColorFilter.tint(MissionTheme.colors.gray)
                    )

                    DropdownMenu(
                        expanded = state.labels.isNotEmpty(),
                        onDismissRequest = viewModel::saveLabels,
                    ) {
                        for (labelEntry in state.labels) {
                            val labelModel = labelEntry.key
                            DropdownMenuItem({ viewModel.selectLabel(labelModel.id) }) {
                                Spacer(modifier = Modifier.height(2.dp))
                                LabelCheckBox(labelModel, labelEntry.value) { viewModel.selectLabel(labelModel.id) }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
internal fun LabelCheckBox(model: LabelModel, checked: Boolean, onClick: () -> Unit) {
    val density = LocalDensity.current

    Row(modifier = Modifier.height(IntrinsicSize.Min), verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f)
                .background(
                    color = if (checked) MissionTheme.colors.secondary else MissionTheme.colors.softSecondary,
                    shape = RoundedCornerShape(35)
                )
                .border(width = 2.dp, color = MissionTheme.colors.secondary, shape = RoundedCornerShape(35))
                .clip(RoundedCornerShape(35))
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.animation.AnimatedVisibility(
                modifier = Modifier.matchParentSize(),
                visible = checked,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Image(
                    painter = painterResource(Res.images.ic_done),
                    contentDescription = "select",
                    modifier = Modifier.matchParentSize(),
                    colorFilter = ColorFilter.tint(MissionTheme.colors.input)
                )
            }
        }
        Spacer(modifier = Modifier.width(2.dp))
        LabelButton(modifier = Modifier.padding(3.dp), label = model.title, color = model.color) {}
    }
}