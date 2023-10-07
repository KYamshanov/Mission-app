package ru.kyamshanov.mission.foundation.impl.login.presentation.screen

import android.os.Parcelable
import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.parcelable.Parcelize
import ru.kyamshanov.mission.core.navigation.api.Screen
import ru.kyamshanov.mission.core.navigation.common.ComposableScreen
import ru.kyamshanov.mission.foundation.impl.login.LoginComposable

@Parcelize
internal actual class AuthenticationComposableScreen : Screen, ComposableScreen, Parcelable {

    @Composable
    override fun Content(componentContext: ComponentContext) {
        LoginComposable()
    }
}