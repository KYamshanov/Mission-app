package ru.kyamshanov.mission.session_front.impl.data.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

/**
 * Dto-Модель - Тело запроса для регистрации пользователя
 * @property login Имя пользователя
 * @property password Пароль
 * @property info Дополнительная информация о пользователе
 */
@Serializable
internal data class UserDto(
    val login: String,
    val password: String,
    val info: Map<String, String>? = null
)