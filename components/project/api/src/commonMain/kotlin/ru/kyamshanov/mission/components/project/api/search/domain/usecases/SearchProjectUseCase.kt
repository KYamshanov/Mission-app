package ru.kyamshanov.mission.components.project.api.search.domain.usecases

import ru.kyamshanov.mission.components.project.api.search.domain.models.PageIndex
import ru.kyamshanov.mission.components.project.api.common.ProjectInfoSlim


interface SearchProjectUseCase {

    suspend fun findAll(pageIndex: PageIndex): Result<List<ProjectInfoSlim>>

    suspend fun searchByName(name: String, pageIndex: PageIndex): Result<List<ProjectInfoSlim>>
}