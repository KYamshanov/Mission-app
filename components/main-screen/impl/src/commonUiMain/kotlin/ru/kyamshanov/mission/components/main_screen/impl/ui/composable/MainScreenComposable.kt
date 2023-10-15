package ru.kyamshanov.mission.components.main_screen.impl.ui.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import ru.kyamshanov.mission.MissionTheme
import ru.kyamshanov.mission.components.main_screen.impl.ui.components.FrontViewModel
import ru.kyamshanov.mission.components.main_screen.impl.ui.components.NavigationBarViewModel
import ru.kyamshanov.mission.components.main_screen.impl.ui.components.SearchViewModel
import ru.kyamshanov.mission.core.ui.components.Cell
import ru.kyamshanov.mission.core.ui.components.CellLine
import ru.kyamshanov.mission.core.ui.components.Search
import ru.kyamshanov.mission.core.ui.extensions.systemBarsPadding


@Composable
internal fun MainScreenComposable(
    navigationBarViewModel: NavigationBarViewModel,
    searchViewModel: SearchViewModel,
    frontViewModel: FrontViewModel,
) {

    SideEffect {
        println("Redraw MainScreen")
    }

    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        backgroundColor = MissionTheme.colors.background,
        bottomBar = {
            NavigationBarComposable(
                navigationBarViewModel = navigationBarViewModel,
            )
        },
    ) {

        val searchPhraseState = rememberSaveable { mutableStateOf("") }
        val searchViewState by searchViewModel.viewState.subscribeAsState()
        val frontViewState by frontViewModel.viewState.subscribeAsState()

        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Search(
                value = searchPhraseState.value, onValueChange = {
                    searchPhraseState.value = it
                    searchViewModel.searchByName(it)
                })

            Spacer(modifier = Modifier.height(10.dp))

            if (searchViewState.isInitialized()) {
                LazyColumn(modifier = Modifier.padding(5.dp)) {
                    searchViewState.items.forEach { project ->
                        item {
                            Text(
                                modifier = Modifier.clickable { searchViewModel.openItem(project.id) },
                                text = project.title,
                                style = MissionTheme.typography.field
                            )
                        }
                    }
                }
            } else if (frontViewState.initialized) {
                Cell {
                    Text(
                        text = "Today`s tasks",
                        style = MissionTheme.typography.titleSecondary
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    for (task in frontViewState.todaysTasks) {
                        Text(
                            modifier = Modifier.clickable { frontViewModel.openItem(task.id) },
                            text = task.title,
                            style = MissionTheme.typography.field
                        )
                    }
                    CellLine()

                    Text(
                        text = "Week`s tasks",
                        style = MissionTheme.typography.titleSecondary
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    for (task in frontViewState.weeksTasks) {
                        Text(
                            modifier = Modifier.clickable { frontViewModel.openItem(task.id) },
                            text = task.title,
                            style = MissionTheme.typography.field
                        )
                    }

                    CellLine()

                    Text(
                        text = "Other tasks",
                        style = MissionTheme.typography.titleSecondary
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    for (task in frontViewState.otherTasks) {
                        Text(
                            modifier = Modifier.clickable { frontViewModel.openItem(task.id) },
                            text = task.title,
                            style = MissionTheme.typography.field
                        )
                    }
                }
            } else {
                Text("Загрузка...")
            }
        }
    }
}