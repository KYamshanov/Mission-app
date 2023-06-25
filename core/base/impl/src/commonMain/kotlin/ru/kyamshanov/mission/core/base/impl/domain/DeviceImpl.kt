package ru.kyamshanov.mission.core.base.impl.domain

import ru.kyamshanov.mission.core.base.api.Device

internal class DeviceImpl constructor() : Device {

    override val info: Map<String, Any>
        get() = mutableMapOf("front-type" to "android", "fingerprint" to "unknown")
}