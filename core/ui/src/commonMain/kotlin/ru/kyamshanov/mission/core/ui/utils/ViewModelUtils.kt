package ru.kyamshanov.mission.core.ui.utils

import androidx.compose.runtime.Composable
import dev.icerock.moko.mvvm.viewmodel.ViewModel

@Composable
expect inline fun <reified T : ViewModel> viewModel(crossinline block: () -> T): T