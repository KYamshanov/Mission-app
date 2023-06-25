package ru.kyamshanov.mission.core.di.impl.koin

import org.koin.dsl.koinApplication

internal object MissionKoinContext {

    private val koinApp = koinApplication { }

    val koin = koinApp.koin
}