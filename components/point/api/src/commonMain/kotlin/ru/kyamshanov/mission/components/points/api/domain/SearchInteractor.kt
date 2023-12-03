package ru.kyamshanov.mission.components.points.api.domain

import ru.kyamshanov.mission.components.points.api.common.LabelModel
import ru.kyamshanov.mission.components.points.api.common.SearchResponse


interface SearchInteractor {

    suspend fun search(phrase: String, labels: List<LabelModel>): Result<SearchResponse>
}