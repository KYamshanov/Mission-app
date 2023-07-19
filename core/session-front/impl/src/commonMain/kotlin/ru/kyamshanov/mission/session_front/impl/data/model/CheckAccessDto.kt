package ru.kyamshanov.mission.session_front.impl.data.model

import kotlinx.serialization.Serializable
import ru.kyamshanov.mission.session_front.api.model.UserRole

/**
 * Dto-model Тело запроса на проверку активности токена
 * @property accessToken Токен доступности
 * @property checkBlock Тогл проверки блокировки токена
 */
@Serializable
internal data class CheckAccessRqDto(
    val accessToken: String,
    val checkBlock: Boolean = false
)

/**
 * Dto-model Тело ответа на проверку активности токена
 * @property status Статус доступности токена
 */
@Serializable
internal data class CheckAccessRsDto(
    val status: AccessStatus,
    val roles: List<UserRoleDto>?
) {

    /**
     * Статус активности токена
     */
    @Serializable
    enum class AccessStatus {

        /**
         * Активный
         */
        ACTIVE,

        /**
         * Просроченный
         */
        EXPIRED,

        /**
         * Блокированный
         */
        BLOCKED
    }
}
