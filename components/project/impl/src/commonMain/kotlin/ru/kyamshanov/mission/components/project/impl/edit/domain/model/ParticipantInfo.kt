package ru.kyamshanov.mission.components.project.impl.edit.domain.model

internal data class ParticipantInfo(
    val userId: String,
    val role: Role,
    val name: String? = null,
    val age: Int? = null,
) {

    enum class Role {
        PARTICIPANT,
        LEADER,
        MENTOR,
    }
}