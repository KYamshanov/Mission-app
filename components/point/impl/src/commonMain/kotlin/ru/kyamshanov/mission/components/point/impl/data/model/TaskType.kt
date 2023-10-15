package ru.kyamshanov.mission.components.point.impl.data.model

import kotlinx.serialization.Serializable

@Serializable
internal enum class TaskType {
    /**
     * today`s
     */
    TODAYS,

    /**
     * week`s
     */
    WEEKS
}