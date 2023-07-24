package ru.kyamshanov.mission.time.impl

import kotlinx.datetime.LocalDateTime

class ComplexMissionDateFormatter {

    fun toDdMmYy(date: LocalDateTime): String =
        "${date.dayOfMonth} ${date.month.name} ${date.year}"
}