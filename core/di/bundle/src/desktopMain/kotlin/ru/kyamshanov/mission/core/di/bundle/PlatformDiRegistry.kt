package ru.kyamshanov.mission.core.di.bundle

import ru.kyamshanov.mission.authorization.impl.di.AuthorizationComponentBuilder
import ru.kyamshanov.mission.core.di.impl.Di

actual fun PlatformDiRegistering() {
    Di.registration(AuthorizationComponentBuilder())
}