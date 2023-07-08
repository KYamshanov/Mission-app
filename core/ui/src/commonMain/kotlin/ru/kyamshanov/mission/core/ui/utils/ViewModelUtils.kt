package ru.kyamshanov.mission.core.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import org.orbitmvi.orbit.ContainerHost

@Composable
expect fun <T : ViewModel> viewModel(block: () -> T): T

@Composable
fun <State : Any, SideEffect : Any> ContainerHost<State, SideEffect>.collectAsState() =
    container.stateFlow.collectAsState()