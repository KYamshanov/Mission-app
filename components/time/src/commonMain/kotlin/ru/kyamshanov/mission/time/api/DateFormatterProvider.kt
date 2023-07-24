package ru.kyamshanov.mission.time.api

interface DateFormatterProvider {

    fun cellDateFormatter(): MissionDateFormatter
}