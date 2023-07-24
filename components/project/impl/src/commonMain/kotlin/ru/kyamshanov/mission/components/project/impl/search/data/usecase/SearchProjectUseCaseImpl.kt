package ru.kyamshanov.mission.components.project.impl.search.data.usecase

import ru.kyamshanov.mission.components.project.api.search.domain.models.PageIndex
import ru.kyamshanov.mission.components.project.api.common.ProjectInfoSlim
import ru.kyamshanov.mission.components.project.api.search.domain.usecases.SearchProjectUseCase
import ru.kyamshanov.mission.components.project.impl.search.data.api.ProjectApi
import ru.kyamshanov.mission.components.project.impl.search.data.model.GetAllProjectsRqDto
import ru.kyamshanov.mission.components.project.impl.search.data.model.toDomain

internal class SearchProjectUseCaseImpl(
    private val projectApi: ProjectApi,
) : SearchProjectUseCase {

    override suspend fun findAll(pageIndex: PageIndex): Result<List<ProjectInfoSlim>> =
        projectApi.loadProjects(
            GetAllProjectsRqDto(
                pageIndex = GetAllProjectsRqDto.PaginationFilter(
                    pageIndex.page,
                    pageIndex.size
                ),
                filter = GetAllProjectsRqDto.SortingFilter(name = "")
            )
        ).mapCatching { it.toDomain() }

    override suspend fun searchByName(
        name: String,
        pageIndex: PageIndex
    ): Result<List<ProjectInfoSlim>> =
        projectApi.loadProjects(
            GetAllProjectsRqDto(
                pageIndex = GetAllProjectsRqDto.PaginationFilter(pageIndex.page, pageIndex.size),
                filter = GetAllProjectsRqDto.SortingFilter(name = name)
            )
        ).mapCatching { it.toDomain() }
}