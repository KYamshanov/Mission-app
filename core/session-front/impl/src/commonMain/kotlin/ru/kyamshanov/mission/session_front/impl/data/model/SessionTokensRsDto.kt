package ru.kyamshanov.mission.session_front.impl.data.model

import kotlinx.serialization.Serializable

/**
 * Dto-model Тело ответа с токенами
 * @property accessToken Токен доступности
 * @property refreshToken Токен обновления
 */

@Serializable
internal data class SessionTokensRsDto(
    val accessToken: AccessTokenDto,
    val refreshToken: String
)
