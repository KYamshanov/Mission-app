package ru.kyamshanov.mission.core.base.impl.domain

import org.w3c.dom.get
import org.w3c.dom.set
import ru.kyamshanov.mission.core.base.api.MissionPreferences

internal actual class MissionPreferencesImpl actual constructor() : MissionPreferences {

    private val storage = kotlinx.browser.localStorage

    override fun saveValue(key: String, value: String) {
        storage[key] = value
    }

    override fun getValue(key: String): String? = storage[key]

    override fun remove(key: String) {
        storage.removeItem(key)
    }
}