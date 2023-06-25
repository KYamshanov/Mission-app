package ru.kyamshanov.mission.session_front.impl.data.model

import ru.kyamshanov.mission.session_front.api.model.UserRole

/**
 * Dto-model Для токена доступности
 * @property accessToken Токен доступности
 * @property roles Роли пользователя
 */
internal data class AccessTokenDto(
    val accessToken: String,
    val roles: List<UserRole>
)
