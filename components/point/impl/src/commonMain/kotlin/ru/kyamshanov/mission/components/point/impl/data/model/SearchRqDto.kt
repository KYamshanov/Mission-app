package ru.kyamshanov.mission.components.point.impl.data.model

import kotlinx.serialization.Serializable

@Serializable
internal data class SearchRqDto(
    val searchPhrase: String?,
    val labels: List<String>
)