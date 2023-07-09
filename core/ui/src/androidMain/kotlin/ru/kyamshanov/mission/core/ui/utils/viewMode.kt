package ru.kyamshanov.mission.core.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import dev.icerock.moko.mvvm.viewmodel.ViewModel

@Composable
actual inline fun <reified T : ViewModel> viewModel(crossinline block: () -> T): T = androidx.lifecycle.viewmodel.compose.viewModel { block.invoke() }