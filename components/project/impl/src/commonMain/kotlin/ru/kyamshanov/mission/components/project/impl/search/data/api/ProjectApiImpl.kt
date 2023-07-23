package ru.kyamshanov.mission.components.project.impl.search.data.api

import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import ru.kyamshanov.mission.core.network.api.RequestFactory
import ru.kyamshanov.mission.core.network.api.utils.retrieveBody
import ru.kyamshanov.mission.components.project.impl.search.data.model.GetAllProjectsRqDto
import ru.kyamshanov.mission.components.project.impl.search.data.model.GetAllProjectsRsDto

internal class ProjectApiImpl(
    private val requestFactory: RequestFactory,
) : ProjectApi {

    override suspend fun loadProjects(body: GetAllProjectsRqDto): Result<GetAllProjectsRsDto> =
        runCatching {
            val response = requestFactory.post("/project/private/get/all") {
                contentType(ContentType.Application.Json)
                setBody(body)
            }
            response.retrieveBody()
        }
}