package ru.kyamshanov.mission.session_front.api.model

/**
 * Репозиторий токенов
 */
interface TokenRepository {
    val accessToken: Token?
    val refreshToken: Token?
}