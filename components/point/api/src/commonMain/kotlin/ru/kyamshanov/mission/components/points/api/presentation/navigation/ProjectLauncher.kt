package ru.kyamshanov.mission.components.points.api.presentation.navigation

import ru.kyamshanov.mission.components.points.api.common.ProjectId

interface ProjectLauncher {

    fun launchCreation()
    fun launchEditing(projectId: ProjectId)
}