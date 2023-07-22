package ru.kyamshanov.mission.session_front.impl.data.model

import kotlinx.serialization.Serializable

@Serializable
enum class UserRoleDto {

    /**
     * Администратор
     */
    ADMIN,

    /**
     * Организатор
     */
    MANAGER
}