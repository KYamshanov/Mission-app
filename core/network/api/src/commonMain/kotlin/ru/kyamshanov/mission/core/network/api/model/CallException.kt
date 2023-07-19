package ru.kyamshanov.mission.core.network.api.model

data class CallException(
    val status: Int,
    override val message: String? = null
) : Exception()