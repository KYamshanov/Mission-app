package ru.kyamshanov.mission.components.points.api.di

import ru.kyamshanov.mission.components.points.api.domain.TaskRepository
import ru.kyamshanov.mission.components.points.api.presentation.navigation.TaskLauncher

interface TaskComponent {
    val taskRepository: TaskRepository
    val launcher: TaskLauncher
}