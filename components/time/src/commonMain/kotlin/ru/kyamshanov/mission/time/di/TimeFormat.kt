package ru.kyamshanov.mission.time.di

import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.QualifierValue


class TimeFormatQualifier(
    private val format: TimeFormat,
) : Qualifier {

    override val value: QualifierValue
        get() = format.name
}

enum class TimeFormat {

    DD_MN_YY,
}