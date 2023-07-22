package ru.kyamshanov.mission.core.base.impl.domain

import ru.kyamshanov.mission.core.base.api.MissionPreferences
import ru.kyamshanov.mission.core.base.impl.database.DatabaseFactory

private const val SHARED_PREFERENCES_KEY = "MissionSharedPreferencesKey"

internal class MissionPreferencesImpl(
    private val databaseFactory: DatabaseFactory,
) : MissionPreferences {

    override suspend fun saveValue(key: String, value: String) {
        val missionDatabase = databaseFactory.getDatabase()
        with(missionDatabase.preferencesQueries) {
            selectByKey(key).executeAsOneOrNull()
                ?.let { missionDatabase.preferencesQueries.update(value, key) }
                ?: missionDatabase.preferencesQueries.insert(key, value)
        }
    }

    override suspend fun getValue(key: String): String? {
        val missionDatabase = databaseFactory.getDatabase()
        return missionDatabase.preferencesQueries.selectByKey(key).executeAsOneOrNull()?.value_
    }

    override suspend fun remove(key: String) {
        //TODO("Need implement")
    }
}