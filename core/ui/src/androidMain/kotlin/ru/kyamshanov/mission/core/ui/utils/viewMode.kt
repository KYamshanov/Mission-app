package ru.kyamshanov.mission.core.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import dev.icerock.moko.mvvm.viewmodel.ViewModel

@Composable
actual fun <T : ViewModel> viewModel(block: () -> T): T = remember { block.invoke() }