package ru.kyamshanov.mission.components.point.impl.data.model

import kotlinx.serialization.Serializable

@Serializable
internal enum class TaskTypeDto {
    /**
     * today`s
     */
    TODAYS,

    /**
     * week`s
     */
    WEEKS
}