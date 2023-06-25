package ru.kyamshanov.mission.session_front.api.di

import ru.kyamshanov.mission.core.di.api.CoreComponent
import ru.kyamshanov.mission.session_front.api.SessionFront
import ru.kyamshanov.mission.session_front.api.SessionInfo

interface SessionFrontComponent : CoreComponent {

    val sessionInfo: SessionInfo

    val sessionFront: SessionFront
}