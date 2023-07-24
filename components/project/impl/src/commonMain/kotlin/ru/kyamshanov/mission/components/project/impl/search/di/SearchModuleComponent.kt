package ru.kyamshanov.mission.components.project.impl.search.di

import ru.kyamshanov.mission.components.project.api.search.di.SearchProjectComponent
import ru.kyamshanov.mission.components.project.api.search.domain.usecases.SearchProjectUseCase
import ru.kyamshanov.mission.core.di.impl.koin.AbstractComponent

internal class SearchModuleComponent : AbstractComponent(), SearchProjectComponent {

    override val searchProjectUseCase: SearchProjectUseCase = resolve()
}