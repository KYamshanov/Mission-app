package ru.kyamshanov.mission.session_front.impl.domain.model

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