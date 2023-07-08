package ru.kyamshanov.mission.session_front.api.model

/**
 * Репозиторий токенов
 */
interface TokenRepository {
    val idToken: Token?
    val accessToken: Token?
    val refreshToken: Token?
}