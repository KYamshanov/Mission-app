package ru.kyamshanov.mission.core.di.impl.koin

import org.koin.dsl.koinApplication

internal object MissionKoinContext {

    val koinApp = koinApplication { }

    val koin = koinApp.koin
}