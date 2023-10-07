package ru.kyamshanov.mission.components.point.impl.search.di

import org.koin.core.module.Module
import org.koin.dsl.module
import ru.kyamshanov.mission.components.point.impl.search.data.api.PointApi
import ru.kyamshanov.mission.components.point.impl.search.data.api.PointApiImpl
import ru.kyamshanov.mission.components.point.impl.search.data.usecase.SearchTaskUseCaseImpl
import ru.kyamshanov.mission.components.points.api.search.di.SearchTaskComponent
import ru.kyamshanov.mission.components.points.api.search.domain.usecases.SearchTaskUseCase
import ru.kyamshanov.mission.core.di.impl.koin.AbstractComponentBuilder

private val SearchTaskModule = module {
    scope<SearchModuleComponent> {
        scoped<PointApi> { PointApiImpl(get()) }
        scoped<SearchTaskUseCase> { SearchTaskUseCaseImpl(get()) }
    }
}

class SearchTaskComponentBuilder : AbstractComponentBuilder<SearchTaskComponent>() {

    override val modules: List<Module> = listOf(SearchTaskModule)

    override fun build(): SearchTaskComponent = SearchModuleComponent()
}