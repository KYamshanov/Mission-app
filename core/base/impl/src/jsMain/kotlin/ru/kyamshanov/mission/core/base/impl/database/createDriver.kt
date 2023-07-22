package ru.kyamshanov.mission.core.base.impl.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.worker.WebWorkerDriver
import org.w3c.dom.Worker
import ru.kyamshanov.mission.core.database.MissionDatabase

internal actual suspend fun createDriver(): SqlDriver {
    val driver = WebWorkerDriver(
        Worker(
            js("""new URL("sqldelight-web-worker-driver", import.meta.url)""")
        )
    )
    MissionDatabase.Schema.create(driver).await()
    return driver
}