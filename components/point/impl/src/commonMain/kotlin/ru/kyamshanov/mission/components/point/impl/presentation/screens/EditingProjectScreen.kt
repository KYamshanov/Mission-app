package ru.kyamshanov.mission.components.point.impl.presentation.screens

import ru.kyamshanov.mission.components.points.api.common.ProjectId
import ru.kyamshanov.mission.components.points.api.common.TaskId
import ru.kyamshanov.mission.core.navigation.api.Screen

internal expect class EditingProjectScreen actual constructor(projectId: ProjectId) : Screen