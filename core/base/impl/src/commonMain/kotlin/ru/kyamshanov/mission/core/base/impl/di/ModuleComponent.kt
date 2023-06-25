package ru.kyamshanov.mission.core.base.impl.di

import ru.kyamshanov.mission.core.base.api.Device
import ru.kyamshanov.mission.core.base.api.MissionPreferences
import ru.kyamshanov.mission.core.base.api.ResourcesProvider
import ru.kyamshanov.mission.core.base.api.di.BaseCoreComponent
import ru.kyamshanov.mission.core.di.impl.koin.AbstractComponent


internal class ModuleComponent : AbstractComponent(), BaseCoreComponent {
    override val missionPreferences: MissionPreferences = resolve()
    override val device: Device = resolve()
    override val resourcesProvider: ResourcesProvider = resolve()

}