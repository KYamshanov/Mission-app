package ru.kyamshanov.mission.core.base.impl.domain

import android.content.Context.MODE_PRIVATE
import ru.kyamshanov.mission.core.base.api.MissionPreferences
import ru.kyamshanov.mission.core.di.impl.Di
import ru.kyamshanov.mission.core.platform_base.di.PlatformBaseComponent


internal actual class MissionPreferencesImpl actual constructor() : MissionPreferences {

    private val preferences by lazy {
        requireNotNull(Di.getComponent<PlatformBaseComponent>()).applicationContext.getSharedPreferences(
            SHARED_PREFERENCES_KEY,
            MODE_PRIVATE
        )
    }

    override fun saveValue(key: String, value: String) {
        preferences.edit()
            .apply {
                putString(key, value)
            }.apply()
    }

    override fun getValue(key: String): String? {
        return preferences.getString(key, null)
    }

    override fun remove(key: String) {
        preferences.edit()
            .apply {
                remove(key)
            }.apply()
    }

    companion object {
        private const val SHARED_PREFERENCES_KEY = "MissionSharedPreferencesKey"
    }

}