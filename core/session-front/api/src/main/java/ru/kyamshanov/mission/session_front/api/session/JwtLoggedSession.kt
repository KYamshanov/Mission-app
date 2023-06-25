package ru.kyamshanov.mission.session_front.api.session

interface JwtLoggedSession : Session {

    val accessToken: String

    val refreshToken: String

    val roles: List<String>
}