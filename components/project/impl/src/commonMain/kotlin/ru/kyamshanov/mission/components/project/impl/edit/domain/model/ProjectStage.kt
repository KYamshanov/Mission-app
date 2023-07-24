package ru.kyamshanov.mission.components.project.impl.edit.domain.model

internal sealed interface ProjectStage {

    object Wait : ProjectStage

    data class InProject(val taskInfo: TaskInfo?) : ProjectStage

    object Finished : ProjectStage
}