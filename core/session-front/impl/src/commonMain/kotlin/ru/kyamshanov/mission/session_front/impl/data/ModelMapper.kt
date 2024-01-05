package ru.kyamshanov.mission.session_front.impl.data

import ru.kyamshanov.mission.session_front.impl.data.model.CheckAccessRsDto
import ru.kyamshanov.mission.session_front.impl.data.model.SessionTokensRsDto
import ru.kyamshanov.mission.session_front.api.model.AccessData
import ru.kyamshanov.mission.session_front.api.model.Token
import ru.kyamshanov.mission.session_front.impl.domain.model.AccessStatus

internal fun SessionTokensRsDto.toDomain() = AccessData(
    accessToken = Token(accessToken.accessToken), refreshToken = Token(refreshToken), roles = accessToken.roles.map { it.name }
)

internal fun CheckAccessRsDto.AccessStatus.toDomain(): AccessStatus = when (this) {
    CheckAccessRsDto.AccessStatus.ACTIVE -> AccessStatus.ACTIVE
    CheckAccessRsDto.AccessStatus.EXPIRED -> AccessStatus.EXPIRED
    CheckAccessRsDto.AccessStatus.BLOCKED -> AccessStatus.BLOCKED
}