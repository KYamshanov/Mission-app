package ru.kyamshanov.mission.session_front.api.session

import ru.kyamshanov.mission.session_front.api.UserInfo
import ru.kyamshanov.mission.session_front.api.model.IdToken

interface LoggedSession : Session {

    val userInfo: UserInfo

    val idToken: IdToken

    suspend fun isActive(): Boolean
}