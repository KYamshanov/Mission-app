package ru.kyamshanov.mission.core.navigation.api

interface ResultProvider {

    fun <T : Any?> get(key: String, defaultValue: T): T

    suspend fun <T : Any?> awaitResult(key: String, defaultValue: T): T

    fun notify(key: String)
}