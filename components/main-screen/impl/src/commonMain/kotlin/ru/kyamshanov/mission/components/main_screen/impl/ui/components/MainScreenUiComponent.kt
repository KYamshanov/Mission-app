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
        SearchProjectUiComponent(childContext(key = "Search")).uiComponent

    override val navigationBarViewModel: NavigationBarViewModel =
        NavigationBarComponentContext(childContext(key = "NavBar"))

    override val frontViewModel: FrontViewModel =
        FrontUiComponent(childContext(key = "Front")).uiComponent
}