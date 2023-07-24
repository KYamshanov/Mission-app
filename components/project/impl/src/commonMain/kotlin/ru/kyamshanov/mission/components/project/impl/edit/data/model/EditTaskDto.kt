package ru.kyamshanov.mission.components.project.impl.edit.data.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

internal typealias EditTaskSetRqDto = List<EditTaskRqDto>

@Serializable
internal data class EditTaskRqDto(
    val taskId: String,
    val title: String? = null,
    val description: String? = null,
    val startAt: LocalDateTime? = null,
    val endAt: LocalDateTime? = null,
    val maxPoints: Int? = null,
    val points: Int? = null,
)


