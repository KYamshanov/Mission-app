package ru.kyamshanov.mission.session_front.impl.data.model

/**
 * Dto-model Тело запроса на обновление токенов
 * @property info Информация о пользователе
 * @property refreshToken Токен обновления
 */
internal data class RefreshRqDto(
    val info: Map<String, Any>,
    val refreshToken: String
)
