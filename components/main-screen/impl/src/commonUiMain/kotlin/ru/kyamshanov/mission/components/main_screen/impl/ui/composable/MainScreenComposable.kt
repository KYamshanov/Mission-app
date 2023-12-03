package ru.kyamshanov.mission.components.main_screen.impl.ui.composable

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import dev.icerock.moko.resources.compose.painterResource
import ru.kyamshanov.mission.MissionTheme
import ru.kyamshanov.mission.components.main_screen.impl.ui.components.FrontViewModel
import ru.kyamshanov.mission.components.main_screen.impl.ui.components.NavigationBarViewModel
import ru.kyamshanov.mission.components.main_screen.impl.ui.components.SearchViewModel
import ru.kyamshanov.mission.components.main_screen.impl.ui.models.SlimItem
import ru.kyamshanov.mission.components.main_screen.impl.ui.models.TaskInfoSlim
import ru.kyamshanov.mission.components.points.api.common.LabelModel
import ru.kyamshanov.mission.core.ui.Res
import ru.kyamshanov.mission.core.ui.components.*
import ru.kyamshanov.mission.core.ui.extensions.systemBarsPadding


@Composable
internal fun MainScreenComposable(
    navigationBarViewModel: NavigationBarViewModel,
    searchViewModel: SearchViewModel,
    frontViewModel: FrontViewModel,
) {
    Column(
        modifier = Modifier.systemBarsPadding(),
    ) {

        val searchViewState by searchViewModel.viewState.subscribeAsState()
        val frontViewState by frontViewModel.viewState.subscribeAsState()

        Column(
            Modifier
                .weight(1f)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {

            Row {

                Search(
                    value = searchViewState.searchPhrase, onValueChange = {
                        searchViewModel.setSearchPhrase(it)
                    }, trailingIcon = {
                        if (searchViewState.searchPhrase.isEmpty() && searchViewState.selectedLabel == SearchViewModel.State.DefaultLabelModel) {
                            Image(
                                painter = painterResource(Res.images.ic_search),
                                contentDescription = "Поиск",
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .size(32.dp),
                                colorFilter = ColorFilter.tint(MissionTheme.colors.gray)
                            )
                        } else {
                            Image(
                                painter = painterResource(Res.images.close),
                                contentDescription = "clear",
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .clickable {
                                        searchViewModel.clear()
                                    }
                                    .size(32.dp),
                                colorFilter = ColorFilter.tint(MissionTheme.colors.gray)
                            )
                        }
                    },
                    leadingIcon = {
                        Box(modifier = Modifier.padding(horizontal = 5.dp)) {
                            val label = searchViewState.selectedLabel
                            LabelButton(label = label.title, color = label.color) { searchViewModel.clickOnLabels() }
                            DropdownMenu(
                                expanded = searchViewState.availableLabels.isNotEmpty(),
                                onDismissRequest = { searchViewModel.clickOnLabels() },
                            ) {
                                for (labelModel in searchViewState.availableLabels) {
                                    DropdownMenuItem({ searchViewModel.setSearchLabel(labelModel) }) {
                                        LabelRadioButton(labelModel, labelModel == searchViewState.selectedLabel)
                                    }
                                }
                            }
                        }
                    }
                )

            }

            Spacer(modifier = Modifier.height(10.dp))

            if (searchViewState.tasks != null && searchViewState.projects != null) {
                Column(modifier = Modifier.padding(5.dp)) {
                    val tasks = searchViewState.tasks?.takeIf { it.isNotEmpty() }
                    val projects = searchViewState.projects?.takeIf { it.isNotEmpty() }

                    if (tasks != null) {
                        Cell(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = "Tasks",
                                style = MissionTheme.typography.field,
                                color = MissionTheme.colors.success,
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                            for (task in tasks) {
                                ComposableItemText(task, frontViewModel) { frontViewModel.openItem(task.id) }
                                Spacer(modifier = Modifier.height(2.dp))
                            }
                        }
                        Spacer(modifier = Modifier.height(5.dp))
                    }
                    if (projects != null) {
                        Cell(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = "Projects",
                                style = MissionTheme.typography.field,
                                color = MissionTheme.colors.secondary,
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                            for (project in projects) {
                                ComposableItemText(project, frontViewModel) { frontViewModel.openItem(project.id) }
                                Spacer(modifier = Modifier.height(2.dp))
                            }
                        }
                    }
                    if (tasks == null && projects == null) {
                        Text(
                            text = "--Not found--",
                            style = MissionTheme.typography.field,
                        )
                    }
                }
            } else if (frontViewState.initialized) {
                Cell(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Today`s tasks",
                        style = MissionTheme.typography.field,
                        color = MissionTheme.colors.success,
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    for (task in frontViewState.todaysTasks) {
                        ComposableItemText(task, frontViewModel) { frontViewModel.openItem(task.id) }
                        Spacer(modifier = Modifier.height(2.dp))
                    }
                }

                Spacer(modifier = Modifier.height(5.dp))
                Cell(modifier = Modifier.fillMaxWidth()) {
                    if (frontViewState.projects.isEmpty()) {
                        Text(
                            text = "Projects",
                            style = MissionTheme.typography.field,
                            color = MissionTheme.colors.secondary,
                        )
                        AlternativeButton(
                            content = {
                                Text(
                                    text = "Ещё нет проектов. Создать проект?",
                                    style = MissionTheme.typography.alternativeButtonStyle + MissionTheme.typography.medium
                                )
                            },
                            onClick = { frontViewModel.createProject() },
                            contentPadding = PaddingValues(3.dp)
                        )
                    } else {
                        Row {
                            Text(
                                text = "Projects",
                                style = MissionTheme.typography.field,
                                color = MissionTheme.colors.secondary,
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Image(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(15))
                                    .clickable { frontViewModel.createProject() }
                                    .fillMaxHeight(),
                                painter = painterResource(Res.images.ic_note_add),
                                contentDescription = "create new project",
                                colorFilter = ColorFilter.tint(MissionTheme.colors.secondary)
                            )
                        }
                        Spacer(modifier = Modifier.height(5.dp))
                        for (task in frontViewState.projects) {
                            ComposableItemText(task, frontViewModel) { frontViewModel.openItem(task.id) }
                            Spacer(modifier = Modifier.height(2.dp))
                        }
                    }
                }
                Spacer(modifier = Modifier.height(5.dp))
                ComplexCell {
                    item {
                        Column {
                            Text(
                                text = "Week`s tasks",
                                style = MissionTheme.typography.field,
                                color = MissionTheme.colors.gold,
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                            for (task in frontViewState.weeksTasks) {
                                ComposableItemText(task, frontViewModel) { frontViewModel.openItem(task.id) }
                                Spacer(modifier = Modifier.height(2.dp))
                            }
                        }
                    }


                    item {
                        Column {
                            Text(
                                text = "Other tasks",
                                style = MissionTheme.typography.field,
                                color = MissionTheme.colors.gray,
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                            for (task in frontViewState.otherTasks) {
                                ComposableItemText(task, frontViewModel) { frontViewModel.openItem(task.id) }
                                Spacer(modifier = Modifier.height(2.dp))
                            }
                        }
                    }
                }
            } else {
                Text("Загрузка...")
            }
        }
        NavigationBarComposable(
            navigationBarViewModel = navigationBarViewModel,
        )
    }
}

@Composable
private fun ComposableItemText(
    item: SlimItem,
    frontViewModel: FrontViewModel,
    onClick: () -> Unit
) {
    if (item is TaskInfoSlim && (item.isHighPriority || item.isLowPriority)) {
        Row {
            Text(
                modifier = Modifier.clickable { onClick() },
                text = item.title,
                style = MissionTheme.typography.titleSecondary
                    .run {
                        if (item.isCompleted) copy(textDecoration = TextDecoration.LineThrough)
                        else this
                    },
            )
            if (item.isHighPriority) {
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
        val density = LocalDensity.current
        var offsetY by remember { mutableStateOf(0f) }
        var movingAvailable by remember { mutableStateOf(true) }
        LaunchedEffect(item) {
            movingAvailable = true
            offsetY = 0f
        }


        Text(
            modifier = Modifier
                .offset(0.dp, (offsetY / density.density).dp)
                .clickable { onClick() }
                .run {
                    if (movingAvailable) pointerInput(item) {
                        detectDragGestures { change, dragAmount ->
                            change.consume()
                            offsetY += dragAmount.y
                            println("Offset: ${(offsetY / density.density).dp}")
                            if ((offsetY / density.density).dp > 10.dp) {
                                println("Exec ${item.id}  ${item.title}")
                                movingAvailable = false
                                frontViewModel.lowerItem(item.id)
                            } else if ((offsetY / density.density).dp < -10.dp) {
                                println("Exec")
                                movingAvailable = false
                                frontViewModel.raiseItem(item.id)
                            }
                        }
                    } else this
                },
            text = item.title,
            style = MissionTheme.typography.titleSecondary
                .run {
                    if (item.isCompleted) copy(textDecoration = TextDecoration.LineThrough)
                    else this
                },
        )
    }
}


@Composable
private fun LabelRadioButton(model: LabelModel, selected: Boolean) {
    Row(modifier = Modifier.height(IntrinsicSize.Min), verticalAlignment = Alignment.CenterVertically) {
        RadioButton(
            selected = selected,
            onClick = null
        )
        Spacer(modifier = Modifier.width(2.dp))
        LabelButton(modifier = Modifier.padding(3.dp), label = model.title, color = model.color) {}
    }
}