package ru.kyamshanov.mission.components.project.impl.edit.di

import ru.kyamshanov.mission.components.project.api.editing.ProjectLauncher
import ru.kyamshanov.mission.components.project.api.editing.di.EditProjectComponent
import ru.kyamshanov.mission.components.project.impl.edit.domain.factories.TeamInteractorFactory
import ru.kyamshanov.mission.components.project.impl.edit.domain.interactor.ProjectInteractor
import ru.kyamshanov.mission.components.project.impl.edit.domain.interactor.TaskPointsAnalyticsInteractor
import ru.kyamshanov.mission.components.project.impl.edit.domain.usecase.TaskStagePresentUseCase
import ru.kyamshanov.mission.core.di.impl.koin.AbstractComponent

internal class EditProjectModuleComponent : AbstractComponent(), EditProjectComponent {

    val teamInteractorFactory: TeamInteractorFactory = resolve()

    val projectInteractor: ProjectInteractor = resolve()

    val pointsInteractor: TaskPointsAnalyticsInteractor = resolve()

    val taskStagePresentUseCase: TaskStagePresentUseCase = resolve()

    override val launcher: ProjectLauncher = resolve()
}