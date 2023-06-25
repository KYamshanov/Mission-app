package ru.kyamshanov.mission.session_front.impl.data.model

/**
 * Dto-model Тело запроса на блокировку сессии по рефреш токена
 * @property refreshToken Рефреш токен
 */
internal data class BlockRefreshRqDto(
    val refreshToken: String
)
