package ru.kyamshanov.mission.components.point.impl.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import dev.icerock.moko.resources.compose.painterResource
import ru.kyamshanov.mission.MissionTheme
import ru.kyamshanov.mission.components.points.api.common.TaskStatus
import ru.kyamshanov.mission.components.points.api.common.TaskType
import ru.kyamshanov.mission.core.ui.Res
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
                Column(modifier = Modifier.align(Alignment.TopStart)) {
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
                    modifier = Modifier.align(Alignment.BottomEnd),
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
                    }
                }
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