package ru.kyamshanov.mission.components.main_screen.impl.ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
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
import ru.kyamshanov.mission.components.points.api.common.TaskPriority
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

        val searchPhraseState = rememberSaveable { mutableStateOf("") }
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
                    value = searchPhraseState.value, onValueChange = {
                        searchPhraseState.value = it
                        searchViewModel.searchByName(it)
                    }, trailingIcon = {
                        if (searchPhraseState.value.isEmpty()) {
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
                                        searchPhraseState.value = ""
                                        searchViewModel.clear()
                                    }
                                    .size(32.dp),
                                colorFilter = ColorFilter.tint(MissionTheme.colors.gray)
                            )
                        }
                    })

            }

            Spacer(modifier = Modifier.height(10.dp))

            if (searchViewState.items != null) {
                Column(modifier = Modifier.padding(5.dp)) {
                    searchViewState.items?.takeIf { it.isNotEmpty() }?.forEach { project ->
                        Text(
                            modifier = Modifier.clickable { searchViewModel.openItem(project.id) },
                            text = project.title,
                            style = MissionTheme.typography.field
                        )
                    } ?: run {
                        Text(
                            text = "--Not found--",
                            style = MissionTheme.typography.field,
                        )
                    }
                }
            } else if (frontViewState.initialized) {
                ComplexCell {
                    item {
                        Column {
                            Text(
                                text = "Today`s tasks",
                                style = MissionTheme.typography.field,
                                color = MissionTheme.colors.success,
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                            for (task in frontViewState.todaysTasks) {
                                ComposableItemText(task) { frontViewModel.openItem(task.id) }
                                Spacer(modifier = Modifier.height(2.dp))
                            }
                        }
                    }

                    item {
                        Column {
                            Text(
                                text = "Week`s tasks",
                                style = MissionTheme.typography.field,
                                color = MissionTheme.colors.gold,
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                            for (task in frontViewState.weeksTasks) {
                                ComposableItemText(task) { frontViewModel.openItem(task.id) }
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
                                ComposableItemText(task) { frontViewModel.openItem(task.id) }
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
        Text(
            modifier = Modifier.clickable { onClick() },
            text = item.title,
            style = MissionTheme.typography.titleSecondary
                .run {
                    if (item.isCompleted) copy(textDecoration = TextDecoration.LineThrough)
                    else this
                },
        )
    }
}