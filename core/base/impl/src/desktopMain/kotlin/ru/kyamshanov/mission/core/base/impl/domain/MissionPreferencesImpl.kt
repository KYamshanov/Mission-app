package ru.kyamshanov.mission.core.base.impl.domain

import ru.kyamshanov.mission.core.base.api.MissionPreferences

internal actual class MissionPreferencesImpl actual constructor() : MissionPreferences {

    private val preferences = java.util.prefs.Preferences.userRoot()

    override fun saveValue(key: String, value: String) {
        preferences.put(key, value)
    }

    override fun getValue(key: String): String? = preferences.get(key, null)

    override fun remove(key: String) {
        preferences.remove(key)
    }
}