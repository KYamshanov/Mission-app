package ru.kyamshanov.mission.core.base.impl.database

import app.cash.sqldelight.db.SqlDriver
import ru.kyamshanov.mission.core.database.MissionDatabase

internal expect fun createDriver(): SqlDriver

internal class DatabaseFactory {


    fun createDatabase(driver: SqlDriver = createDriver()): MissionDatabase = MissionDatabase(driver)

}


