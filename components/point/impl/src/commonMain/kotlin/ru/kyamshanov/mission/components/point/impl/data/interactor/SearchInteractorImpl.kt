package ru.kyamshanov.mission.components.point.impl.data.interactor

import ru.kyamshanov.mission.components.point.impl.data.api.PointApi
import ru.kyamshanov.mission.components.point.impl.data.model.SearchRqDto
import ru.kyamshanov.mission.components.point.impl.data.model.SearchRsDto
import ru.kyamshanov.mission.components.point.impl.data.model.toDomain
import ru.kyamshanov.mission.components.point.impl.data.usecase.toDomain
import ru.kyamshanov.mission.components.point.impl.domain.interactor.LabelInteractor
import ru.kyamshanov.mission.components.points.api.common.LabelModel
import ru.kyamshanov.mission.components.points.api.common.ProjectSlim
import ru.kyamshanov.mission.components.points.api.common.SearchResponse
import ru.kyamshanov.mission.components.points.api.common.TaskSlim
import ru.kyamshanov.mission.components.points.api.domain.SearchInteractor

internal class SearchInteractorImpl(
    private val api: PointApi
) : SearchInteractor {

    override suspend fun search(phrase: String, labels: List<LabelModel>): Result<SearchResponse> = runCatching {
        when {
            labels.isNotEmpty() -> {
                api.search(
                    SearchRqDto(
                        searchPhrase = phrase.takeIf { it.isNotBlank() },
                        labels.map { it.id })
                )
            }

            else -> {
                require(phrase.isNotBlank()) { "Phrase canmnot be blank for search" }
                api.search(phrase)
            }
        }.toDomain()
    }

}

private fun SearchRsDto.toDomain() = SearchResponse(
    tasks = tasks.map { it.toDomain() },
    projects = projects.map { it.toDomain() }
)

private fun SearchRsDto.ProjectSlim.toDomain() = ProjectSlim(
    id, title
)

private fun SearchRsDto.TaskSlim.toDomain() = TaskSlim(
    id, title, priority?.toDomain(), status.toDomain(), type.toDomain(), offset = 0
)
