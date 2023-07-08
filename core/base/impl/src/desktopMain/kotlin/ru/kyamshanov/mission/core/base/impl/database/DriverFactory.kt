package ru.kyamshanov.mission.core.base.impl.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import ru.kyamshanov.mission.core.database.MissionDatabase

actual fun createDriver(): SqlDriver {
    val driver: SqlDriver = JdbcSqliteDriver("jdbc:sqlite:mission")
    MissionDatabase.Schema.create(driver)
    return driver
}