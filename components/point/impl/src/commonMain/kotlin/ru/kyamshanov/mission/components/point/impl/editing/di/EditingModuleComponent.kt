package ru.kyamshanov.mission.components.point.impl.editing.di

import ru.kyamshanov.mission.components.point.impl.editing.domain.GetTaskUseCase
import ru.kyamshanov.mission.components.points.api.editing.di.EditingTaskComponent
import ru.kyamshanov.mission.components.points.api.editing.di.EditingTaskLauncher
import ru.kyamshanov.mission.components.points.api.search.di.SearchTaskComponent
import ru.kyamshanov.mission.components.points.api.search.domain.usecases.SearchTaskUseCase
import ru.kyamshanov.mission.core.di.impl.koin.AbstractComponent

internal class EditingModuleComponent : AbstractComponent(), EditingTaskComponent {
    override val launcher: EditingTaskLauncher = resolve()
    val getTaskUseCase: GetTaskUseCase = resolve()
}