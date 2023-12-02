package ru.kyamshanov.mission.components.points.api.di

import ru.kyamshanov.mission.components.points.api.domain.ProjectsRepository
import ru.kyamshanov.mission.components.points.api.domain.TaskRepository
import ru.kyamshanov.mission.components.points.api.presentation.navigation.ProjectLauncher
import ru.kyamshanov.mission.components.points.api.presentation.navigation.TaskLauncher

interface TaskComponent {

    val taskRepository: TaskRepository

    val launcher: TaskLauncher

    val projectsRepository: ProjectsRepository

    val projectLauncher: ProjectLauncher
}