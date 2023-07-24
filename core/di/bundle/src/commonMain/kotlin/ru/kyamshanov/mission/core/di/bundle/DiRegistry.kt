package ru.kyamshanov.mission.core.di.bundle

import ru.kyamshanov.mission.components.main_screen.impl.di.MainScreenComponentBuilder
import ru.kyamshanov.mission.components.project.impl.edit.di.EditProjectComponentBuilder
import ru.kyamshanov.mission.components.project.impl.search.di.SearchProjectComponentBuilder
import ru.kyamshanov.mission.core.base.impl.di.BaseCoreComponentBuilder
import ru.kyamshanov.mission.core.di.impl.Di
import ru.kyamshanov.mission.core.navigation.impl.di.NavigationComponentBuilder
import ru.kyamshanov.mission.core.network.impl.di.NetworkComponentBuilder
import ru.kyamshanov.mission.foundation.impl.login.di.AuthenticationComponentBuilder
import ru.kyamshanov.mission.foundation.impl.splash_screen.di.SplashScreenComponentBuilder
import ru.kyamshanov.mission.session_front.impl.di.SessionFrontComponentBuilder
import ru.kyamshanov.mission.time.di.TimeFormatterComponentBuilder

object DiRegistry {

    fun registering() {
        Di.registration(NavigationComponentBuilder())
        Di.registration(AuthenticationComponentBuilder())
        Di.registration(BaseCoreComponentBuilder())
        Di.registration(SessionFrontComponentBuilder())
        Di.registration(NetworkComponentBuilder())
        Di.registration(SplashScreenComponentBuilder())
        Di.registration(MainScreenComponentBuilder())
        Di.registration(SearchProjectComponentBuilder())
        Di.registration(TimeFormatterComponentBuilder())
        Di.registration(EditProjectComponentBuilder())
    }

}