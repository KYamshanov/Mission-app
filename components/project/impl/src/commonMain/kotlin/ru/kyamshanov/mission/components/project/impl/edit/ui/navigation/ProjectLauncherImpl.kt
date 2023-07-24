package ru.kyamshanov.mission.components.project.impl.edit.ui.navigation

import ru.kyamshanov.mission.components.project.api.editing.ProjectLauncher
import ru.kyamshanov.mission.core.navigation.api.Navigator
import ru.kyamshanov.mission.components.project.impl.edit.ui.screen.ProjectScreen

class ProjectLauncherImpl(
    private val navigator: Navigator
) : ProjectLauncher {

    override fun launch(projectId : String) {
        navigator.navigateTo(ProjectScreen(projectId = projectId))
    }
}