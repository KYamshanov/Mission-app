package ru.kyamshanov.mission.core.base.impl.domain

import ru.kyamshanov.mission.core.base.api.MissionPreferences

private const val SHARED_PREFERENCES_KEY = "MissionSharedPreferencesKey"

class MissionPreferencesImpl : MissionPreferences {


    override fun saveValue(key: String, value: String) {
        //TODO("Need implement")
    }

    override fun getValue(key: String): String? {
        //TODO("Need implement")
        return null
    }

    override fun remove(key: String) {
        //TODO("Need implement")
    }
}