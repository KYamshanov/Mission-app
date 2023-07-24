package ru.kyamshanov.mission.components.project.impl.edit.ui.model

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import ru.kyamshanov.mission.components.project.api.common.ProjectId
import ru.kyamshanov.mission.components.project.impl.edit.domain.model.ProjectInfo

@Parcelize
internal data class ProjectInfoSlim(
    val id: ProjectId,
    val name: String,
) : Parcelable

internal fun ProjectInfo.toSlim() = ProjectInfoSlim(
    id = id,
    name = title
)