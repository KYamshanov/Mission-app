package ru.kyamshanov.mission.session_front.impl.data.model

import kotlinx.serialization.Serializable

/**
 * Dto-model Тело запроса на обновление токенов
 * @property info Информация о пользователе
 * @property refreshToken Токен обновления
 */
@Serializable
internal data class RefreshRqDto(
    val info: Map<String, String>,
    val refreshToken: String
)
