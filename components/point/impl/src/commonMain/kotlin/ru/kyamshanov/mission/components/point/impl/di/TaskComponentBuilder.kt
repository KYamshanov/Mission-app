package ru.kyamshanov.mission.components.point.impl.di

import org.koin.core.module.Module
import org.koin.dsl.module
import ru.kyamshanov.mission.components.point.impl.data.api.PointApi
import ru.kyamshanov.mission.components.point.impl.data.api.PointApiImpl
import ru.kyamshanov.mission.components.point.impl.data.interactor.TaskInteractorImpl
import ru.kyamshanov.mission.components.point.impl.data.usecase.GetTaskUseCaseImpl
import ru.kyamshanov.mission.components.point.impl.data.usecase.TaskRepositoryImpl
import ru.kyamshanov.mission.components.point.impl.domain.interactor.TaskInteractor
import ru.kyamshanov.mission.components.point.impl.domain.usecase.GetTaskUseCase
import ru.kyamshanov.mission.components.point.impl.presentation.launcher.TaskLauncherImpl
import ru.kyamshanov.mission.components.points.api.di.TaskComponent
import ru.kyamshanov.mission.components.points.api.domain.TaskRepository
import ru.kyamshanov.mission.components.points.api.presentation.navigation.TaskLauncher
import ru.kyamshanov.mission.core.di.impl.koin.AbstractComponentBuilder

private val TaskModule = module {
    scope<TaskModuleComponent> {
        scoped<TaskRepository> { TaskRepositoryImpl(get()) }
        scoped<PointApi> { PointApiImpl(get()) }
        scoped<GetTaskUseCase> { GetTaskUseCaseImpl(get()) }
        scoped<TaskLauncher> { TaskLauncherImpl(get()) }
        scoped<TaskInteractor> { TaskInteractorImpl(get()) }
    }
}

class TaskComponentBuilder : AbstractComponentBuilder<TaskComponent>() {

    override val modules: List<Module> = listOf(TaskModule)

    override fun build(): TaskComponent = TaskModuleComponent()
}