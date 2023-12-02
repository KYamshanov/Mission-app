package ru.kyamshanov.mission.components.point.impl.presentation.launcher

import ru.kyamshanov.mission.components.point.impl.presentation.screens.CreationProjectScreen
import ru.kyamshanov.mission.components.point.impl.presentation.screens.CreationTaskScreen
import ru.kyamshanov.mission.components.point.impl.presentation.screens.EditingProjectScreen
import ru.kyamshanov.mission.components.point.impl.presentation.screens.EditingTaskScreen
import ru.kyamshanov.mission.components.points.api.common.ProjectId
import ru.kyamshanov.mission.components.points.api.common.TaskId
import ru.kyamshanov.mission.components.points.api.presentation.navigation.ProjectLauncher
import ru.kyamshanov.mission.components.points.api.presentation.navigation.TaskLauncher
import ru.kyamshanov.mission.core.navigation.api.Navigator

internal class ProjectLauncherImpl(
    private val navigator: Navigator
) : ProjectLauncher {
    override fun launchCreation() {
        navigator.navigateTo(CreationProjectScreen())
    }

    override fun launchEditing(projectId: ProjectId) {
        navigator.navigateTo(EditingProjectScreen(projectId))
    }
}