package ru.kyamshanov.mission.core.navigation.common.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.instancekeeper.instanceKeeper
import com.arkivanov.essenty.lifecycle.LifecycleRegistry

@Composable
actual fun composeComponentContext(sourceComponentContext: ComponentContext?): DefaultComponentContext {
    val viewModelStoreOwner = requireNotNull(LocalViewModelStoreOwner.current)
    return DefaultComponentContext(
        lifecycle = sourceComponentContext?.lifecycle ?: LifecycleRegistry(),
        instanceKeeper = viewModelStoreOwner.instanceKeeper()
    )
}