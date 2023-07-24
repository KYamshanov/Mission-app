package ru.kyamshanov.mission.components.project.impl.edit.ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import dev.icerock.moko.resources.compose.painterResource
import ru.kyamshanov.mission.MissionTheme
import ru.kyamshanov.mission.components.project.impl.edit.domain.model.ParticipantInfo
import ru.kyamshanov.mission.components.project.impl.edit.ui.components.ParticipantsListViewModel
import ru.kyamshanov.mission.core.ui.Res
import ru.kyamshanov.mission.core.ui.components.Cell
import ru.kyamshanov.mission.core.ui.components.SecondaryButton
import ru.kyamshanov.mission.core.ui.components.Surface
import ru.kyamshanov.mission.core.ui.components.SwipeableRow
import ru.kyamshanov.mission.core.ui.components.TopBar
import ru.kyamshanov.mission.components.project.impl.edit.ui.model.ProjectInfoSlim

@Composable
internal fun ParticipantsListComposable(
    projectInfoSlim: ProjectInfoSlim,
    viewModel: ParticipantsListViewModel
) {
    val screenState by viewModel.screenState.subscribeAsState()

    Surface(
        topContent = {
            TopBar(title = projectInfoSlim.name, navigationListener = viewModel::clickOnBack)
        },
        bottomContent = {
            if (screenState.teamEditingScheme.isTeamEditable) {
                SecondaryButton(
                    modifier = Modifier.padding(16.dp).fillMaxWidth(),
                    label = "Добавить участника",
                    onClick = viewModel::clickOnAddParticipant,
                )
            }
        }
    ) {
        Column {
            val teamInfo = screenState.teamInfo
            teamInfo.mentor.also { participant ->
                MentorCell(
                    participant = participant,
                    clickOnChange = viewModel::clickOnChangeMentor,
                    editable = screenState.teamEditingScheme.isMentorEditable,
                    clickOnRemove = { participant?.let { viewModel.clickOnRemove(it) } }
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            teamInfo.leader?.also { participant ->
                ParticipantCell(
                    participant = participant,
                    clickOnLeaderToggle = { viewModel.clickOnLeaderToggle(participant) },
                    clickOnRemove = { viewModel.clickOnRemove(participant) },
                    editable = screenState.teamEditingScheme.isLeaderEditable
                )
            } ?: {

            }
            teamInfo.participants.forEach { participant ->
                Spacer(modifier = Modifier.height(10.dp))
                ParticipantCell(
                    participant = participant,
                    clickOnLeaderToggle = { viewModel.clickOnLeaderToggle(participant) },
                    clickOnRemove = { viewModel.clickOnRemove(participant) },
                    editable = screenState.teamEditingScheme.isLeaderEditable
                )
            }
        }
    }
}

@Composable
private fun ParticipantCell(
    participant: ParticipantInfo,
    editable: Boolean,
    clickOnLeaderToggle: () -> Unit,
    clickOnRemove: () -> Unit,
) = Cell {
    SwipeableRow(
        modifier = Modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        hiddenContent = {
            if (editable) {
                Spacer(modifier = Modifier.width(5.dp))
                Image(
                    painter = painterResource(Res.images.close_circle),
                    contentDescription = "Удалить",
                    modifier = Modifier.clickable { clickOnRemove.invoke() },
                    colorFilter = ColorFilter.tint(MissionTheme.colors.wrong)
                )
            }
        }
    ) {

        Text(
            text = if (participant.role == ParticipantInfo.Role.LEADER) "${participant.name.orEmpty()} (Капитан)" else participant.name.orEmpty(),
            style = MissionTheme.typography.title.run { if (participant.role == ParticipantInfo.Role.LEADER) this + MissionTheme.typography.gold else this }
        )
        Spacer(modifier = Modifier.weight(1f))
        if (participant.role == ParticipantInfo.Role.LEADER) {
            Image(
                painter = painterResource(Res.images.crown_circle),
                contentDescription = "Сделать лидером",
                modifier = Modifier
                    .run { if (editable) clickable { clickOnLeaderToggle.invoke() } else this },
                colorFilter = ColorFilter.tint(MissionTheme.colors.gold),
            )
        } else {
            Image(
                painter = painterResource(Res.images.crown_circle),
                contentDescription = "Сделать лидером",
                modifier = Modifier.clickable { clickOnLeaderToggle.invoke() },
                colorFilter = ColorFilter.tint(MissionTheme.colors.gray)
            )
        }
    }
}

@Composable
private fun MentorCell(
    participant: ParticipantInfo?,
    editable: Boolean,
    clickOnChange: () -> Unit,
    clickOnRemove: () -> Unit,
) = Cell {
    SwipeableRow(
        modifier = Modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        hiddenContent = {
            if (editable) {
                Spacer(modifier = Modifier.width(5.dp))
                Image(
                    painter = painterResource(Res.images.close_circle),
                    contentDescription = "Удалить",
                    modifier = Modifier.clickable { clickOnRemove.invoke() },
                    colorFilter = ColorFilter.tint(MissionTheme.colors.wrong)
                )
            }
        }
    ) {
        when {
            participant != null -> Text(
                text = "${participant.name} (Наставник)",
                style = MissionTheme.typography.title + MissionTheme.typography.green
            )

            else -> Text(
                text = "Отсутствует (Наставник)",
                style = MissionTheme.typography.title + MissionTheme.typography.green
            )
        }
        if (editable) {
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(Res.images.square_edit_outline),
                contentDescription = "Удалить",
                modifier = Modifier.clickable { clickOnChange.invoke() },
                colorFilter = ColorFilter.tint(MissionTheme.colors.darkSecondary),
                contentScale = ContentScale.None
            )
        }
    }
}