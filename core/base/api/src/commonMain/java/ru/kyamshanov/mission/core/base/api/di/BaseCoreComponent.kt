package ru.kyamshanov.mission.core.base.api.di

import ru.kyamshanov.mission.core.base.api.Device
import ru.kyamshanov.mission.core.base.api.MissionPreferences
import ru.kyamshanov.mission.core.base.api.ResourcesProvider
import ru.kyamshanov.mission.core.di.api.CoreComponent

interface BaseCoreComponent : CoreComponent {

    val missionPreferences: MissionPreferences

    val device: Device

    val resourcesProvider: ResourcesProvider
}