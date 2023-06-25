package ru.kyamshanov.mission.session_front.api.session

data class UnauthorizedSession(val reason: Throwable) : Session