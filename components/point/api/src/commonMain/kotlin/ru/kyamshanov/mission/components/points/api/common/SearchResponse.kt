package ru.kyamshanov.mission.components.points.api.common

data class SearchResponse(
    val tasks : List<TaskSlim>,
    val projects : List<ProjectSlim>
)
