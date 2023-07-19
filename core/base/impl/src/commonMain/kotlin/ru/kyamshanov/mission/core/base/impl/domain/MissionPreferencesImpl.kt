package ru.kyamshanov.mission.core.base.impl.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.kyamshanov.mission.core.base.api.MissionPreferences
import ru.kyamshanov.mission.core.database.MissionDatabase

private const val SHARED_PREFERENCES_KEY = "MissionSharedPreferencesKey"

internal class MissionPreferencesImpl(
    private val missionDatabase: MissionDatabase,
) : MissionPreferences {


    override suspend fun saveValue(key: String, value: String) {
            with(missionDatabase.preferencesQueries) {
                selectByKey(key).executeAsOneOrNull()
                    ?.let { missionDatabase.preferencesQueries.update(value, key) }
                    ?: missionDatabase.preferencesQueries.insert(key, value)
            }
    }

    override fun getValue(key: String): String? =
        missionDatabase.preferencesQueries.selectByKey(key).executeAsOneOrNull()?.value_

    override fun remove(key: String) {
        //TODO("Need implement")
    }
}