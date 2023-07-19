package ru.kyamshanov.mission.session_front.impl.data.model

import kotlinx.serialization.Serializable

/**
 * Dto-model Для токена доступности
 * @property accessToken Токен доступности
 * @property roles Роли пользователя
 */
@Serializable
internal data class AccessTokenDto(
    val accessToken: String,
    val roles: List<UserRoleDto> = emptyList()
)
