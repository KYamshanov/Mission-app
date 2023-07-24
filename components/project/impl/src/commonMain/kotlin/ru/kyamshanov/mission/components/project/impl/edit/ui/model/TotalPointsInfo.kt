package ru.kyamshanov.mission.components.project.impl.edit.ui.model

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import ru.kyamshanov.mission.components.project.impl.edit.domain.model.TaskPointsInfo

@Parcelize
internal data class TotalPointsInfo(
    val projectName: String,
    val stagePoints: List<TaskPointsInfo>,
) : Parcelable {

    val totalPoints: Int
        get() = stagePoints.sumOf { it.currentPoints ?: 0 }

    constructor() : this("", emptyList())
}