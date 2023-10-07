package ru.kyamshanov.mission.core.navigation.api

import kotlinx.coroutines.flow.SharedFlow

interface ResultBus {

    val sharedFlow: SharedFlow<Pair<String, Any>>

    fun <T : Any?> get(key: String, defaultValue: T): T

    suspend fun <T : Any?> awaitResult(key: String, defaultValue: T): T

    fun notify(key: String)
}