package ru.kyamshanov.mission.core.di.common

import ru.kyamshanov.mission.core.di.impl.Di
import ru.kyamshanov.mission.core.login_screen.impl.di.AuthenticationComponentBuilder
import ru.kyamshanov.mission.core.navigation.impl.di.NavigationComponentBuilder

object DiRegistry {

    fun registering() {
        Di.registration(NavigationComponentBuilder())
        Di.registration(AuthenticationComponentBuilder())
    }

}