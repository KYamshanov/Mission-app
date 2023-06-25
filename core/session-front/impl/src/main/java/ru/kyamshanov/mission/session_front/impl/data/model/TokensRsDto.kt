package ru.kyamshanov.mission.session_front.impl.data.model

/**
 * Dto-model Тело ответа с токенами
 * @property accessToken Токен доступности
 * @property refreshToken Токен обновления
 */

internal data class TokensRsDto(
    val accessToken: AccessTokenDto,
    val refreshToken: String
)
