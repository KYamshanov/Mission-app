package ru.kyamshanov.mission.components.point.impl.search.di

import ru.kyamshanov.mission.components.points.api.search.di.SearchTaskComponent
import ru.kyamshanov.mission.components.points.api.search.domain.usecases.SearchTaskUseCase
import ru.kyamshanov.mission.core.di.impl.koin.AbstractComponent

internal class SearchModuleComponent : AbstractComponent(), SearchTaskComponent {
    override val searchTaskUseCase: SearchTaskUseCase = resolve()
}