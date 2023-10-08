package ru.kyamshanov.mission.components.points.api.di

import ru.kyamshanov.mission.components.points.api.domain.SearchTaskUseCase
import ru.kyamshanov.mission.components.points.api.presentation.navigation.TaskLauncher

interface TaskComponent {
    val searchTaskUseCase: SearchTaskUseCase
    val launcher: TaskLauncher
}