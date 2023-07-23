package ru.kyamshanov.mission.components.main_screen.impl.ui.components

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext

internal interface MainScreenUiComponent {

    val searchProjectViewModel: SearchProjectViewModel

    val navigationBarViewModel: NavigationBarViewModel
}

internal class MainScreenUiComponentImpl(
    componentContext: ComponentContext
) : MainScreenUiComponent, ComponentContext by componentContext {

    override val searchProjectViewModel: SearchProjectViewModel =
        SearchProjectUiComponent(childContext(key = "Search")).uiComponent

    override val navigationBarViewModel: NavigationBarViewModel =
        NavigationBarComponentContext(childContext(key = "NavBar"))
}