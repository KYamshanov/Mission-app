package ru.kyamshanov.mission.core.ui.utils

import androidx.compose.runtime.Composable
import dev.icerock.moko.mvvm.viewmodel.ViewModel

@Composable
expect fun <T : ViewModel> viewModel(block: () -> T): T