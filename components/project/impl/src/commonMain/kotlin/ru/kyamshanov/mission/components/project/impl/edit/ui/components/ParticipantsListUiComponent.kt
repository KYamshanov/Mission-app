package ru.kyamshanov.mission.components.project.impl.edit.ui.components

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import ru.kyamshanov.mission.components.project.api.editing.di.EditProjectComponent
import ru.kyamshanov.mission.components.project.impl.edit.di.EditProjectModuleComponent
import ru.kyamshanov.mission.components.project.impl.edit.domain.model.ParticipantInfo
import ru.kyamshanov.mission.core.navigation.api.di.NavigationComponent
import ru.kyamshanov.mission.core.navigation.common.utils.di
import ru.kyamshanov.mission.core.navigation.common.utils.diInternal
import ru.kyamshanov.mission.core.navigation.common.utils.getValue
import ru.kyamshanov.mission.project_view.impl.ui.model.ParticipantsListScreenState
import ru.kyamshanov.mission.components.project.impl.edit.ui.model.ProjectInfoSlim

internal interface ParticipantsListViewModel {

    val screenState: Value<ParticipantsListScreenState>
    fun clickOnBack()
    fun clickOnLeaderToggle(participant: ParticipantInfo)
    fun clickOnRemove(participant: ParticipantInfo)
    fun clickOnAddParticipant()
    fun clickOnChangeMentor()
}

internal class ParticipantsListUiComponent(
    private val projectInfoSlim: ProjectInfoSlim,
    private val context: ComponentContext
) : ComponentContext by context {

    private val internalApi by requireNotNull(instanceKeeper.diInternal<EditProjectComponent, EditProjectModuleComponent>())
    private val navigationComponent by requireNotNull(instanceKeeper.di<NavigationComponent>())

    val viewModel: ParticipantsListViewModel =
        instanceKeeper.getOrCreate(::ParticipantsListRetainedInstance)

    private val teamInteractorFactory get() = internalApi.teamInteractorFactory
    private val navigator get() = navigationComponent.navigator

    private inner class ParticipantsListRetainedInstance : InstanceKeeper.Instance,
        ParticipantsListViewModel {

        private val viewModelScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

        private val teamInteractor =
            teamInteractorFactory.create(
                projectId = projectInfoSlim.id,
                coroutineScope = viewModelScope
            )

        private val _screenState = MutableValue(ParticipantsListScreenState())
        override val screenState = _screenState

        init {
            viewModelScope.launch(Dispatchers.Default) {
                teamInteractor.participantsStateFlow.collect { teamInfo ->
                    _screenState.update { it.copy(teamInfo = teamInfo) }
                }
            }
            viewModelScope.launch(Dispatchers.Default) {
                teamInteractor.teamEditingSchemeStateFlow.collect { teamEditingScheme ->
                    _screenState.update { it.copy(teamEditingScheme = teamEditingScheme) }
                }
            }
        }

        override fun clickOnBack() {
            navigator.exit()
        }

        override fun clickOnLeaderToggle(participant: ParticipantInfo) {
            viewModelScope.launch {
                teamInteractor.setLeader(participant)
                    .onSuccess { }
            }
        }

        override fun clickOnRemove(participant: ParticipantInfo) {
            viewModelScope.launch {
                teamInteractor.removeParticipant(participant)
            }
        }

        override fun clickOnAddParticipant() {
            /* viewModelScope.launch {
                 val foundUser = findingUserLauncher.suspendLaunch() ?: return@launch
                 teamInteractor.addParticipant(
                     ParticipantInfo(foundUser.id, ParticipantInfo.Role.PARTICIPANT)
                 )
             }*/
        }

        override fun clickOnChangeMentor() {
            /*  viewModelScope.launch {
                  val foundUser =
                      findingUserLauncher.suspendLaunch(SearchStrategy.AllByProject(projectId = projectInfoSlim.id.value))
                          ?: return@launch
                  teamInteractor.setMentor(
                      ParticipantInfo(foundUser.id, ParticipantInfo.Role.MENTOR)
                  )
              }*/
        }

        override fun onDestroy() {
            viewModelScope.cancel()
        }
    }
}