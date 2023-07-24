package ru.kyamshanov.mission.components.project.impl.edit.domain.factories

import kotlinx.coroutines.CoroutineScope
import ru.kyamshanov.mission.components.project.api.common.ProjectId
import ru.kyamshanov.mission.components.project.impl.edit.data.api.ProjectApi
import ru.kyamshanov.mission.components.project.impl.edit.data.interactor.TeamInteractorImpl
import ru.kyamshanov.mission.components.project.impl.edit.domain.interactor.TeamInteractor
import ru.kyamshanov.mission.components.project.impl.edit.domain.usecase.FetchTeamUseCase
import ru.kyamshanov.mission.components.project.impl.edit.domain.usecase.FilterParticipantsUseCase
import ru.kyamshanov.mission.session_front.api.SessionInfo

internal class TeamInteractorFactory(
    private val projectApi: ProjectApi,
    private val fetchTeamUseCase: FetchTeamUseCase,
    private val filterParticipantsUseCase: FilterParticipantsUseCase,
    private val sessionInfo: SessionInfo,
) {

    fun create(
        projectId: ProjectId,
        coroutineScope: CoroutineScope,
    ): TeamInteractor =
        TeamInteractorImpl(
            projectId = projectId,
            coroutineScope = coroutineScope,
            projectApi = projectApi,
            fetchTeamUseCase = fetchTeamUseCase,
            filterParticipantsUseCase = filterParticipantsUseCase,
            sessionInfo = sessionInfo
        )
}