package ru.kyamshanov.mission.components.project.impl.edit.data.api

import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.kyamshanov.mission.components.project.impl.edit.data.model.AddParticipantRqDto
import ru.kyamshanov.mission.components.project.impl.edit.data.model.EditProjectRqDto
import ru.kyamshanov.mission.components.project.impl.edit.data.model.EditTaskSetRqDto
import ru.kyamshanov.mission.components.project.impl.edit.data.model.GetTeamRsDto
import ru.kyamshanov.mission.components.project.impl.edit.data.model.ProjectInfoDto
import ru.kyamshanov.mission.components.project.impl.edit.data.model.RemoveParticipantRqDto
import ru.kyamshanov.mission.components.project.impl.edit.data.model.SetRoleRqDto
import ru.kyamshanov.mission.core.network.api.RequestFactory
import ru.kyamshanov.mission.core.network.api.utils.retrieveBody

internal class ProjectApiImpl(
    private val requestFactory: RequestFactory,
) : ProjectApi {

    override suspend fun getProject(projectId: String): ProjectInfoDto =
        withContext(Dispatchers.Default) {
            requestFactory.get("/project/private/find?id=${projectId}") {
                contentType(ContentType.Application.Json)
            }.retrieveBody()
        }

    override suspend fun getTeam(projectId: String): GetTeamRsDto =
        withContext(Dispatchers.Default) {
            requestFactory.get("/project/private/team?project=${projectId}") {
                contentType(ContentType.Application.Json)
            }.retrieveBody()
        }

    override suspend fun getManagedTeam(projectId: String): GetTeamRsDto =
        withContext(Dispatchers.Default) {
            requestFactory.get("/project/manager/team?project=${projectId}") {
                contentType(ContentType.Application.Json)
            }.retrieveBody()
        }

    override suspend fun editProject(body: EditProjectRqDto): Unit =
        withContext(Dispatchers.Default) {
            requestFactory.post("/project/manager/project/edit") {
                contentType(ContentType.Application.Json)
                setBody(body)
            }.retrieveBody()
        }

    override suspend fun editTasks(body: EditTaskSetRqDto): Unit =
        withContext(Dispatchers.Default) {
            requestFactory.post("/project/manager/task/edit/set") {
                contentType(ContentType.Application.Json)
                setBody(body)
            }.retrieveBody()
        }

    override suspend fun setRole(body: SetRoleRqDto): Unit = withContext(Dispatchers.Default) {
        requestFactory.post("/project/manager/role") {
            contentType(ContentType.Application.Json)
            setBody(body)
        }.retrieveBody()
    }

    override suspend fun addParticipant(body: AddParticipantRqDto): Unit =
        withContext(Dispatchers.Default) {
            requestFactory.post("/project/manager/participant/add") {
                contentType(ContentType.Application.Json)
                setBody(body)
            }.retrieveBody()
        }

    override suspend fun removeParticipant(body: RemoveParticipantRqDto): Unit =
        withContext(Dispatchers.Default) {
            requestFactory.post("/project/manager/participant/remove") {
                contentType(ContentType.Application.Json)
                setBody(body)
            }.retrieveBody()
        }
}