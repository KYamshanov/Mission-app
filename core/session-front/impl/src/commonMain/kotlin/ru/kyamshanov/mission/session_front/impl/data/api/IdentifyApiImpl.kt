package ru.kyamshanov.mission.session_front.impl.data.api

import io.ktor.http.ContentType
import io.ktor.http.contentType
import ru.kyamshanov.mission.core.network.api.RequestFactory
import ru.kyamshanov.mission.core.network.api.utils.retrieveBody
import ru.kyamshanov.mission.session_front.impl.data.model.IdentifyRsDto

internal class IdentifyApiImpl constructor(
    private val requestFactory: RequestFactory,
) : IdentifyApi {

    override suspend fun identify(): Result<IdentifyRsDto> = runCatching {
        val response = requestFactory.post("private/id/mission/identify") {
            contentType(ContentType.Application.Json)
        }
        response.retrieveBody()
    }
}