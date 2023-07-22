package ru.kyamshanov.mission.core.base.impl.database

import app.cash.sqldelight.db.SqlDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.kyamshanov.mission.core.database.MissionDatabase

internal suspend expect fun createDriver(): SqlDriver

internal interface DatabaseFactory {


    suspend fun getDatabase(): MissionDatabase

}

internal class AsyncDatabaseFactory(
    private val dbProvider: suspend () -> SqlDriver = ::createDriver
) : DatabaseFactory {

    override suspend fun getDatabase(): MissionDatabase =
        withContext(Dispatchers.Default) {
            MissionDatabase(dbProvider.invoke())
        }

}


