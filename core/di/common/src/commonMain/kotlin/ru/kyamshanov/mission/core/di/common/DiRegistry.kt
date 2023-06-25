package ru.kyamshanov.mission.core.di.common

import ru.kyamshanov.mission.core.base.impl.di.BaseCoreComponentBuilder
import ru.kyamshanov.mission.core.di.impl.Di
import ru.kyamshanov.mission.core.login_screen.impl.di.AuthenticationComponentBuilder
import ru.kyamshanov.mission.core.navigation.impl.di.NavigationComponentBuilder
import ru.kyamshanov.mission.core.network.impl.di.NetworkComponentBuilder
import ru.kyamshanov.mission.session_front.impl.di.SessionFrontComponentBuilder

object DiRegistry {

    fun registering() {
        Di.registration(NavigationComponentBuilder())
        Di.registration(AuthenticationComponentBuilder())
        Di.registration(BaseCoreComponentBuilder())
        Di.registration(SessionFrontComponentBuilder())
        Di.registration(NetworkComponentBuilder())
    }

}