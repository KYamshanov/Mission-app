package ru.kyamshanov.mission.components.project.impl.edit.data.api

import ru.kyamshanov.mission.components.project.impl.edit.data.model.AddParticipantRqDto
import ru.kyamshanov.mission.components.project.impl.edit.data.model.EditProjectRqDto
import ru.kyamshanov.mission.components.project.impl.edit.data.model.EditTaskSetRqDto
import ru.kyamshanov.mission.components.project.impl.edit.data.model.GetTeamRsDto
import ru.kyamshanov.mission.components.project.impl.edit.data.model.ProjectInfoDto
import ru.kyamshanov.mission.components.project.impl.edit.data.model.RemoveParticipantRqDto
import ru.kyamshanov.mission.components.project.impl.edit.data.model.SetRoleRqDto

internal interface ProjectApi {

    suspend fun getProject(projectId: String): ProjectInfoDto

    suspend fun getTeam(projectId: String): GetTeamRsDto

    suspend fun getManagedTeam(projectId: String): GetTeamRsDto
    suspend fun editProject(body: EditProjectRqDto)
    suspend fun editTasks(body: EditTaskSetRqDto)

    suspend fun setRole(body: SetRoleRqDto)

    suspend fun addParticipant(body: AddParticipantRqDto)

    suspend fun removeParticipant(body: RemoveParticipantRqDto)
}