package ru.kyamshanov.mission.components.project.api.search.di

import ru.kyamshanov.mission.components.project.api.search.domain.usecases.SearchProjectUseCase

interface SearchProjectComponent {

    val searchProjectUseCase: SearchProjectUseCase
}