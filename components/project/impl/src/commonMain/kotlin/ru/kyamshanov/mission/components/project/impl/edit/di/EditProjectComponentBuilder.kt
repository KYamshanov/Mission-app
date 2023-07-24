package ru.kyamshanov.mission.components.project.impl.edit.di

import org.koin.core.module.Module
import org.koin.dsl.module
import ru.kyamshanov.mission.components.project.api.editing.ProjectLauncher
import ru.kyamshanov.mission.components.project.api.editing.di.EditProjectComponent
import ru.kyamshanov.mission.components.project.impl.edit.data.api.ProjectApi
import ru.kyamshanov.mission.components.project.impl.edit.data.api.ProjectApiImpl
import ru.kyamshanov.mission.components.project.impl.edit.data.interactor.ProjectInteractorImpl
import ru.kyamshanov.mission.components.project.impl.edit.data.interactor.TaskPointsAnalyticsInteractorImpl
import ru.kyamshanov.mission.components.project.impl.edit.data.usecase.FetchTeamUseCaseImpl
import ru.kyamshanov.mission.components.project.impl.edit.data.usecase.GetProjectUseCaseImpl
import ru.kyamshanov.mission.components.project.impl.edit.domain.factories.TeamInteractorFactory
import ru.kyamshanov.mission.components.project.impl.edit.domain.interactor.ProjectInteractor
import ru.kyamshanov.mission.components.project.impl.edit.domain.interactor.TaskPointsAnalyticsInteractor
import ru.kyamshanov.mission.components.project.impl.edit.domain.usecase.FetchTeamUseCase
import ru.kyamshanov.mission.components.project.impl.edit.domain.usecase.FilterParticipantsUseCase
import ru.kyamshanov.mission.components.project.impl.edit.domain.usecase.FilterParticipantsUseCaseImpl
import ru.kyamshanov.mission.components.project.impl.edit.domain.usecase.GetProjectUseCase
import ru.kyamshanov.mission.components.project.impl.edit.domain.usecase.TaskStagePresentUseCase
import ru.kyamshanov.mission.components.project.impl.edit.ui.navigation.ProjectLauncherImpl
import ru.kyamshanov.mission.components.project.impl.edit.ui.usecase.TaskStagePresentUseCaseImpl
import ru.kyamshanov.mission.core.di.impl.koin.AbstractComponentBuilder
import ru.kyamshanov.mission.time.di.TimeFormat
import ru.kyamshanov.mission.time.di.TimeFormatQualifier

private val editProjectModule = module {
    scope<EditProjectModuleComponent> {
        scoped<ProjectInteractor> { ProjectInteractorImpl(get(), get(), get()) }
        scoped<ProjectApi> { ProjectApiImpl(get()) }
        scoped<TaskPointsAnalyticsInteractor> { TaskPointsAnalyticsInteractorImpl(get(), get()) }
        scoped { TeamInteractorFactory(get(), get(), get(), get()) }
        scoped<FetchTeamUseCase> { FetchTeamUseCaseImpl(get(), get()) }
        scoped<GetProjectUseCase> { GetProjectUseCaseImpl(get(), get()) }
        scoped<FilterParticipantsUseCase> { FilterParticipantsUseCaseImpl() }
        scoped<TaskStagePresentUseCase> {
            TaskStagePresentUseCaseImpl(get(), get(TimeFormatQualifier(TimeFormat.DD_MN_YY)))
        }
        scoped<ProjectLauncher> { ProjectLauncherImpl(get()) }
    }
}

class EditProjectComponentBuilder : AbstractComponentBuilder<EditProjectComponent>() {

    override val modules: List<Module> = listOf(editProjectModule)

    override fun build(): EditProjectComponent = EditProjectModuleComponent()
}