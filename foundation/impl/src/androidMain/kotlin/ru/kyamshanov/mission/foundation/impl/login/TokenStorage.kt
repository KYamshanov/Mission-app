package ru.kyamshanov.mission.foundation.impl.login


object TokenStorage {
    var accessToken: String? = null
    var refreshToken: String? = null
    var idToken: String? = null
}