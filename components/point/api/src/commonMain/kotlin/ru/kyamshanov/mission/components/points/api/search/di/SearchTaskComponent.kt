package ru.kyamshanov.mission.components.points.api.search.di

import ru.kyamshanov.mission.components.points.api.search.domain.usecases.SearchTaskUseCase

interface SearchTaskComponent {

    val searchTaskUseCase: SearchTaskUseCase
}