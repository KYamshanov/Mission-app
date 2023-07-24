package ru.kyamshanov.mission.time.di

import org.koin.core.module.Module
import org.koin.dsl.module
import ru.kyamshanov.mission.core.di.impl.koin.AbstractComponentBuilder
import ru.kyamshanov.mission.time.api.DateFormatterProvider
import ru.kyamshanov.mission.time.api.MissionDateFormatter
import ru.kyamshanov.mission.time.impl.ComplexMissionDateFormatter


private val timeFormatterModule = module {
    single { ComplexMissionDateFormatter() }
    single(TimeFormatQualifier(TimeFormat.DD_MN_YY)) {
        MissionDateFormatter { get<ComplexMissionDateFormatter>().toDdMmYy(it) }
    }

    single<DateFormatterProvider>(TimeFormatQualifier(TimeFormat.DD_MN_YY)) {
        object : DateFormatterProvider {
            override fun cellDateFormatter(): MissionDateFormatter =
                get(TimeFormatQualifier(TimeFormat.DD_MN_YY))
        }
    }
}

class TimeFormatterComponentBuilder : AbstractComponentBuilder<Unit>() {

    override val modules: List<Module> = listOf(timeFormatterModule)

    override fun build() = Unit
}
