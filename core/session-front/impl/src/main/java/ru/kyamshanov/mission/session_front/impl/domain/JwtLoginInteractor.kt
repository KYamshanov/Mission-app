package ru.kyamshanov.mission.session_front.impl.domain

import ru.kyamshanov.mission.session_front.impl.domain.model.AccessData
import ru.kyamshanov.mission.session_front.impl.domain.model.AccessStatus
import ru.kyamshanov.mission.session_front.impl.domain.model.Token

internal interface JwtLoginInteractor {

    suspend fun login(login: String, password: CharSequence): Result<AccessData>

    suspend fun check(accessToken: Token): Result<AccessStatus>

    suspend fun refresh(refresh: Token): Result<AccessData>

    suspend fun blockRefresh(refresh: Token): Result<Unit>
}