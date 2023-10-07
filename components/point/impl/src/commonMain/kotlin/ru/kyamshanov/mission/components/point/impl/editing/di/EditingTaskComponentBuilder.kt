package ru.kyamshanov.mission.components.point.impl.editing.di

import org.koin.core.module.Module
import org.koin.dsl.module
import ru.kyamshanov.mission.components.point.impl.editing.data.GetTaskUseCaseImpl
import ru.kyamshanov.mission.components.point.impl.editing.data.api.PointApi
import ru.kyamshanov.mission.components.point.impl.editing.data.api.PointApiImpl
import ru.kyamshanov.mission.components.point.impl.editing.domain.GetTaskUseCase
import ru.kyamshanov.mission.components.point.impl.editing.presentation.EditingTaskLauncherImpl
import ru.kyamshanov.mission.components.points.api.editing.di.EditingTaskComponent
import ru.kyamshanov.mission.components.points.api.editing.di.EditingTaskLauncher
import ru.kyamshanov.mission.core.di.impl.koin.AbstractComponentBuilder

private val EditingTaskModule = module {
    scope<EditingModuleComponent> {
        scoped<PointApi> { PointApiImpl(get()) }
        scoped<EditingTaskLauncher> { EditingTaskLauncherImpl(get()) }
        scoped<GetTaskUseCase> { GetTaskUseCaseImpl(get()) }
    }
}

class EditingTaskComponentBuilder : AbstractComponentBuilder<EditingTaskComponent>() {

    override val modules: List<Module> = listOf(EditingTaskModule)

    override fun build(): EditingTaskComponent = EditingModuleComponent()
}