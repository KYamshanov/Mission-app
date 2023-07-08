package ru.kyamshanov.mission.session_front.impl.di

import ru.kyamshanov.mission.core.di.impl.koin.AbstractComponent
import ru.kyamshanov.mission.session_front.api.SessionFront
import ru.kyamshanov.mission.session_front.api.SessionInfo
import ru.kyamshanov.mission.session_front.api.di.SessionFrontComponent
import ru.kyamshanov.mission.session_front.api.model.TokenRepository


internal class ModuleComponent : AbstractComponent(), SessionFrontComponent {
    override val sessionInfo: SessionInfo = resolve()
    override val sessionFront: SessionFront = resolve()
}