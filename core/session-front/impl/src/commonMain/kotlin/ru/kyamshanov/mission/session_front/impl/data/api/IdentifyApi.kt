package ru.kyamshanov.mission.session_front.impl.data.api

import ru.kyamshanov.mission.session_front.impl.data.model.IdentifyRsDto

internal interface IdentifyApi {

    suspend fun identify(): Result<IdentifyRsDto>
}