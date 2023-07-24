package ru.kyamshanov.mission.components.project.impl.search.data.api

import ru.kyamshanov.mission.components.project.impl.search.data.model.GetAllProjectsRqDto
import ru.kyamshanov.mission.components.project.impl.search.data.model.GetAllProjectsRsDto

internal interface ProjectApi {

    suspend fun loadProjects(body: GetAllProjectsRqDto): Result<GetAllProjectsRsDto>
}