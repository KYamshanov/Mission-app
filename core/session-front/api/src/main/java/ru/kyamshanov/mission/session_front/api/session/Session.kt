package ru.kyamshanov.mission.session_front.api.session

import ru.kyamshanov.mission.session_front.api.model.Token
import ru.kyamshanov.mission.session_front.api.model.TokenRepository
import ru.kyamshanov.mission.session_front.api.model.UserInfo

/**
 * Интерфейс сессии
 */
sealed interface Session

interface LoggingSession : Session, TokenRepository

/**
 * Авторизованная сессия
 */

interface LoggedSession : Session, TokenRepository {

    /** Информация о пользователе */
    val userInfo: UserInfo
}

/**
 * Неавторизованная сесси по причие [reason]
 */
data class UnauthorizedSession(val reason: Throwable) : Session

/**
 * Неидентифицированная сессия
 */
object UnidentifiedSession : Session