package ru.kyamshanov.mission.core.base.impl.di

import org.koin.dsl.module
import ru.kyamshanov.mission.core.base.api.Device
import ru.kyamshanov.mission.core.base.api.MissionPreferences
import ru.kyamshanov.mission.core.base.api.ResourcesProvider
import ru.kyamshanov.mission.core.base.impl.database.DatabaseFactory
import ru.kyamshanov.mission.core.base.impl.domain.DeviceImpl
import ru.kyamshanov.mission.core.base.impl.domain.MissionPreferencesImpl
import ru.kyamshanov.mission.core.base.impl.domain.ResourcesProviderImpl
import ru.kyamshanov.mission.core.database.MissionDatabase

internal val BaseCoreModule = module {
    single<MissionPreferences> { MissionPreferencesImpl(get()) }
    single<Device> { DeviceImpl() }
    single<ResourcesProvider> { ResourcesProviderImpl() }
    single<MissionDatabase> { DatabaseFactory().createDatabase() }
}