package ru.kyamshanov.mission.components.project.impl.edit.domain.gateway

import ru.kyamshanov.mission.components.project.impl.edit.domain.model.ProjectInfo

internal interface ProjectGateway {

    suspend fun getProject(projectId: String): ProjectInfo
}