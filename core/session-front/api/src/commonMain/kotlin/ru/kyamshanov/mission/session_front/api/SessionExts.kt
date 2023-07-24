package ru.kyamshanov.mission.session_front.api

import ru.kyamshanov.mission.session_front.api.model.UserRole
import ru.kyamshanov.mission.session_front.api.session.LoggedSession

/**
 * Провериь начилие роли пользователя([role]) в сессии [this]
 * Если пользователь не авторизован будет возвращен false
 *
 * @return true если есть роль инче false.
 *
 */
fun SessionInfo.hasRole(role: UserRole): Boolean =
    (session as? LoggedSession)?.userInfo?.roles?.contains(role) == true