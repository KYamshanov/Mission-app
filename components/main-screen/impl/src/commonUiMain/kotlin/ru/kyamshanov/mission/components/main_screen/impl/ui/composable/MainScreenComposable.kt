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
import ru.kyamshanov.mission.components.main_screen.impl.ui.components.NavigationBarViewModel
import ru.kyamshanov.mission.components.main_screen.impl.ui.components.SearchProjectViewModel
import ru.kyamshanov.mission.core.ui.components.Search
import ru.kyamshanov.mission.core.ui.extensions.systemBarsPadding


@Composable
internal fun MainScreenComposable(
    navigationBarViewModel: NavigationBarViewModel,
    searchProjectViewModel: SearchProjectViewModel,
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
        val projects by searchProjectViewModel.projectsState.subscribeAsState()

        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Search(
                value = searchPhraseState.value, onValueChange = {
                    searchPhraseState.value = it
                    searchProjectViewModel.searchByName(it)
                })

            Spacer(modifier = Modifier.height(10.dp))

            LazyColumn(modifier = Modifier.padding(5.dp)) {
                projects.forEach { project ->
                    item {
                        Text(
                            modifier = Modifier.clickable { searchProjectViewModel.openProject(project.id) },
                            text = project.title,
                            style = MissionTheme.typography.field
                        )
                    }
                }
            }
        }
    }
}