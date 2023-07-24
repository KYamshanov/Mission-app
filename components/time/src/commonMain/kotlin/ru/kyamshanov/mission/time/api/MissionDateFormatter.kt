package ru.kyamshanov.mission.time.api

import kotlinx.datetime.LocalDateTime

fun interface MissionDateFormatter {

    operator fun invoke(date: LocalDateTime): String
}