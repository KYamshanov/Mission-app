package ru.kyamshanov.mission.components.project.impl.edit.domain.model

internal data class TeamInfo(
    val mentor: ParticipantInfo?,
    val leader: ParticipantInfo?,
    val participants: List<ParticipantInfo>,
) {

    constructor() : this(null, null, emptyList())
}