package ru.kyamshanov.mission.components.point.impl.di

import ru.kyamshanov.mission.components.point.impl.domain.interactor.TaskInteractor
import ru.kyamshanov.mission.components.point.impl.domain.usecase.GetTaskUseCase
import ru.kyamshanov.mission.components.points.api.di.TaskComponent
import ru.kyamshanov.mission.components.points.api.domain.TaskRepository
import ru.kyamshanov.mission.components.points.api.presentation.navigation.TaskLauncher
import ru.kyamshanov.mission.core.di.impl.koin.AbstractComponent

internal class TaskModuleComponent : AbstractComponent(), TaskComponent {
    override val taskRepository: TaskRepository = resolve()
    override val launcher: TaskLauncher = resolve()

    val getTaskUseCase: GetTaskUseCase = resolve()
    val interactor: TaskInteractor = resolve()
}