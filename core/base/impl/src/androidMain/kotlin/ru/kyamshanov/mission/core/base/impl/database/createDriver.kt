package ru.kyamshanov.mission.core.base.impl.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import ru.kyamshanov.mission.core.database.MissionDatabase
import ru.kyamshanov.mission.core.di.impl.Di
import ru.kyamshanov.mission.core.platform_base.di.PlatformBaseComponent

internal actual fun createDriver(): SqlDriver =
    AndroidSqliteDriver(
        schema = MissionDatabase.Schema,
        context = requireNotNull(Di.getComponent<PlatformBaseComponent>()).applicationContext,
        name = "mission.db"
    )