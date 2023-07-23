package ru.kyamshanov.mission.components.project.impl.search.data.model

import kotlinx.serialization.Serializable
import ru.kyamshanov.mission.components.project.api.common.ProjectInfoSlim

@Serializable
internal data class GetAllProjectsRqDto(
    val pageIndex: PaginationFilter? = null,
    val filter: SortingFilter,
) {
    @Serializable
    data class PaginationFilter(
        val page: Int,
        val size: Int,
    )
    @Serializable
    data class SortingFilter(
        val name: String,
    )
}

@Serializable
internal data class GetAllProjectsRsDto(
    val projects: List<ProjectInfoDto>,
)

internal fun GetAllProjectsRsDto.toDomain(): List<ProjectInfoSlim> = projects.map {
    ProjectInfoSlim(it.id, it.title, it.description)
}