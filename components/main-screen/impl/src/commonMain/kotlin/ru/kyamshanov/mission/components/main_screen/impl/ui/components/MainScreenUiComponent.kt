package ru.kyamshanov.mission.components.main_screen.impl.ui.components

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext

internal interface MainScreenUiComponent {

    val searchViewModel: SearchViewModel

    val navigationBarViewModel: NavigationBarViewModel

    val frontViewModel: FrontViewModel
}

internal class MainScreenUiComponentImpl(
    componentContext: ComponentContext
) : MainScreenUiComponent, ComponentContext by componentContext {

    override val searchViewModel: SearchViewModel =
        SearchProjectUiComponent(this).viewModel

    override val navigationBarViewModel: NavigationBarViewModel =
        NavigationBarComponentContext(this)

    override val frontViewModel: FrontViewModel =
        FrontUiComponent(this).uiComponent
}