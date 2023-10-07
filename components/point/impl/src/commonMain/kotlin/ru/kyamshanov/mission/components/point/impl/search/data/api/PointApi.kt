package ru.kyamshanov.mission.components.point.impl.search.data.api

import ru.kyamshanov.mission.components.point.impl.search.data.model.AttachedTasksResponseDto

internal interface PointApi {

    suspend fun allProjects(): AttachedTasksResponseDto
}