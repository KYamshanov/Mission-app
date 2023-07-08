package ru.kyamshanov.mission.session_front.impl.data.model

import kotlinx.serialization.Serializable
import ru.kyamshanov.mission.session_front.api.model.Token

@Serializable
internal data class IdentifyRsDto(
    val idToken: String,
)

internal fun IdentifyRsDto.toDomain() = Token(
    value = idToken
)