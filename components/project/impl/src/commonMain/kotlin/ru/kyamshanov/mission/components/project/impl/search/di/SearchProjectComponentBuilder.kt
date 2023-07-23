package ru.kyamshanov.mission.components.project.impl.search.di

import org.koin.core.module.Module
import org.koin.dsl.module
import ru.kyamshanov.mission.components.project.api.search.di.SearchProjectComponent
import ru.kyamshanov.mission.components.project.api.search.domain.usecases.SearchProjectUseCase
import ru.kyamshanov.mission.components.project.impl.search.data.api.ProjectApi
import ru.kyamshanov.mission.components.project.impl.search.data.api.ProjectApiImpl
import ru.kyamshanov.mission.components.project.impl.search.data.usecase.SearchProjectUseCaseImpl
import ru.kyamshanov.mission.core.di.impl.koin.AbstractComponentBuilder

private val searchProjectModule = module {
    scope<SearchModuleComponent> {
        scoped<SearchProjectUseCase> { SearchProjectUseCaseImpl(get()) }
        scoped<ProjectApi> { ProjectApiImpl(get()) }
    }
}

class SearchProjectComponentBuilder : AbstractComponentBuilder<SearchProjectComponent>() {

    override val modules: List<Module> = listOf(searchProjectModule)

    override fun build(): SearchProjectComponent = SearchModuleComponent()
}