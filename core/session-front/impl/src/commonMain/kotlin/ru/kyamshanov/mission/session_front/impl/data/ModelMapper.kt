package ru.kyamshanov.mission.session_front.impl.data

import ru.kyamshanov.mission.session_front.impl.data.model.CheckAccessRsDto
import ru.kyamshanov.mission.session_front.impl.data.model.TokensRsDto
import ru.kyamshanov.mission.session_front.impl.domain.model.AccessData
import ru.kyamshanov.mission.session_front.impl.domain.model.AccessStatus

internal fun TokensRsDto.toDomain() = AccessData(
    accessToken = accessToken.accessToken, refreshToken = refreshToken, roles = accessToken.roles.map { it.name }
)

internal fun CheckAccessRsDto.AccessStatus.toDomain(): AccessStatus = when (this) {
    CheckAccessRsDto.AccessStatus.ACTIVE -> AccessStatus.ACTIVE
    CheckAccessRsDto.AccessStatus.EXPIRED -> AccessStatus.EXPIRED
    CheckAccessRsDto.AccessStatus.BLOCKED -> AccessStatus.BLOCKED
}