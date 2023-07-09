package ru.kyamshanov.mission.core.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import dev.icerock.moko.mvvm.viewmodel.ViewModel

@Composable
actual inline fun <T : ViewModel> viewModel(crossinline block: () -> T): T =
    remember { block.invoke() }