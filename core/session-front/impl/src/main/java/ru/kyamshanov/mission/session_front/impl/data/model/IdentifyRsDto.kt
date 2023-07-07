package ru.kyamshanov.mission.session_front.impl.data.model

import ru.kyamshanov.mission.session_front.api.model.Token

internal data class IdentifyRsDto(
    val idToken: String,
)

internal fun IdentifyRsDto.toDomain() = Token(
    value = idToken
)