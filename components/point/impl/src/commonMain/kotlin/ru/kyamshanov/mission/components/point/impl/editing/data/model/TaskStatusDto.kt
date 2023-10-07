package ru.kyamshanov.mission.components.point.impl.editing.data.model

import kotlinx.serialization.Serializable


@Serializable
enum class TaskStatusDto {
    CREATED, IN_PROCESSING, COMPLETED
}