package ru.kyamshanov.mission.components.project.impl.edit.domain.model

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import ru.kyamshanov.mission.components.project.api.common.TaskId

@Parcelize
internal data class TaskPointsInfo(
    val taskId: TaskId,
    val taskTitle: String,
    val currentPoints: Int?,
    val maxPoints: Int,
) : Parcelable
