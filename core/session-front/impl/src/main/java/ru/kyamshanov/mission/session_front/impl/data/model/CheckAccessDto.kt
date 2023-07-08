package ru.kyamshanov.mission.session_front.impl.data.model

import ru.kyamshanov.mission.session_front.api.model.UserRole

/**
 * Dto-model Тело запроса на проверку активности токена
 * @property accessToken Токен доступности
 * @property checkBlock Тогл проверки блокировки токена
 */
internal data class CheckAccessRqDto(
    val accessToken: String,
    val checkBlock: Boolean = false
)

/**
 * Dto-model Тело ответа на проверку активности токена
 * @property status Статус доступности токена
 */
internal data class CheckAccessRsDto(
    val status: AccessStatus,
    val roles: List<UserRoleDto>?
) {

    /**
     * Статус активности токена
     */
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
