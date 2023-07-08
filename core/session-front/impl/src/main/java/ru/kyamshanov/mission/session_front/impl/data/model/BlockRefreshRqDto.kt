package ru.kyamshanov.mission.session_front.impl.data.model

import kotlinx.serialization.Serializable

/**
 * Dto-model Тело запроса на блокировку сессии по рефреш токена
 * @property refreshToken Рефреш токен
 */
@Serializable
internal data class BlockRefreshRqDto(
    val refreshToken: String
)
