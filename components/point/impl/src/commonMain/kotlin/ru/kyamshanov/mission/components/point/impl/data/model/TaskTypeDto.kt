package ru.kyamshanov.mission.components.point.impl.data.model

import kotlinx.serialization.Serializable
import ru.kyamshanov.mission.components.points.api.common.TaskType

@Serializable
internal enum class TaskTypeDto {
    /**
     * today`s
     */
    TODAYS,

    /**
     * week`s
     */
    WEEKS,

    UNKNOWN,
}

internal fun TaskTypeDto.toDomain(): TaskType? = when (this) {
    TaskTypeDto.TODAYS -> TaskType.TODAYS
    TaskTypeDto.WEEKS -> TaskType.WEEKS
    TaskTypeDto.UNKNOWN -> null
}

internal fun TaskType?.toDto(): TaskTypeDto = when (this) {
    TaskType.TODAYS -> TaskTypeDto.TODAYS
    TaskType.WEEKS -> TaskTypeDto.WEEKS
    null -> TaskTypeDto.UNKNOWN
}