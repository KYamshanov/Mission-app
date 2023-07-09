package ru.kyamshanov.mission.foundation.impl.login.presentation.screen

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.parcelable.Parcelize
import ru.kyamshanov.mission.components.main_screen.api.di.MainScreenComponent
import ru.kyamshanov.mission.core.di.impl.Di
import ru.kyamshanov.mission.core.navigation.common.ComposableScreen
import ru.kyamshanov.mission.core.navigation.common.utils.composeComponentContext
import ru.kyamshanov.mission.foundation.api.login.di.AuthenticationComponent
import ru.kyamshanov.mission.foundation.impl.login.di.ModuleComponent
import ru.kyamshanov.mission.foundation.impl.login.presentation.component.AuthenticationUiComponent
import ru.kyamshanov.mission.foundation.impl.login.presentation.composable.AuthenticationComponent

@Parcelize
internal class AuthenticationComposableScreen : ComposableScreen {

    @Composable
    override fun Content(componentContext: ComponentContext) {

        val moduleComponent: ModuleComponent =
            requireNotNull(Di.getInternalComponent<AuthenticationComponent, ModuleComponent>())
        val mainScreenLauncher = requireNotNull(Di.getComponent<MainScreenComponent>()).launcher

        val viewModel = AuthenticationUiComponent(
            componentContext = composeComponentContext(componentContext),
            authenticationInteractor = moduleComponent.authenticationInteractor,
            mainScreenLauncher = mainScreenLauncher
        ).ViewModel()

        AuthenticationComponent(viewModel = viewModel)
    }

}