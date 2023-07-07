package ru.kyamshanov.mission.session_front.api.session

import ru.kyamshanov.mission.session_front.api.model.Token
import ru.kyamshanov.mission.session_front.api.model.TokenRepository
import ru.kyamshanov.mission.session_front.api.model.UserInfo

/**
 * Интерфейс сессии
 */
sealed interface Session

/**
 * Авторизованная сессия
 */

interface LoggedSession : Session {

    /** Информация о пользователе */
    val userInfo: UserInfo

    /** Хранилище токенов */
    val tokenRepository: TokenRepository

}

/**
 * Неавторизованная сесси по причие [reason]
 */
data class UnauthorizedSession(val reason: Throwable) : Session

/**
 * Неидентифицированная сессия
 */
object UnidentifiedSession : Session