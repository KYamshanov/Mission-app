package ru.kyamshanov.mission.core.base.api

interface MissionPreferences {

    suspend fun saveValue(key: String, value: String)

    suspend fun getValue(key: String): String?

    suspend fun remove(key: String)
}