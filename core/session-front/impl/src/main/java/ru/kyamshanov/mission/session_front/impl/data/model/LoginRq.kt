package ru.kyamshanov.mission.session_front.impl.data.model

import kotlinx.serialization.Serializable

@Serializable
internal data class LoginRq(
    val login: String,
    val password: String,
    val info: Map<String, String>? = null
)