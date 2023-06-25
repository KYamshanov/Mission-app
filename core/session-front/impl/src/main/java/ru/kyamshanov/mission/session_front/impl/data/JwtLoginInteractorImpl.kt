package ru.kyamshanov.mission.session_front.impl.data

import ru.kyamshanov.mission.core.base.api.Device
import ru.kyamshanov.mission.session_front.impl.data.api.AuthenticationApi
import ru.kyamshanov.mission.session_front.impl.data.model.BlockRefreshRqDto
import ru.kyamshanov.mission.session_front.impl.data.model.CheckAccessRqDto
import ru.kyamshanov.mission.session_front.impl.data.model.RefreshRqDto
import ru.kyamshanov.mission.session_front.impl.data.model.UserDto
import ru.kyamshanov.mission.session_front.impl.domain.JwtLoginInteractor
import ru.kyamshanov.mission.session_front.impl.domain.model.AccessData
import ru.kyamshanov.mission.session_front.impl.domain.model.AccessStatus
import ru.kyamshanov.mission.session_front.impl.domain.model.Token

internal class JwtLoginInteractorImpl constructor(
    private val authenticationApi: AuthenticationApi,
    private val device: Device
) : JwtLoginInteractor {

    override suspend fun login(login: String, password: CharSequence): Result<AccessData> =
        authenticationApi.login(UserDto(login, password, info = device.info))
            .map { it.toDomain() }

    override suspend fun check(accessToken: Token): Result<AccessStatus> =
        authenticationApi.check(CheckAccessRqDto(accessToken, false))
            .map { it.status.toDomain() }

    override suspend fun refresh(refresh: Token): Result<AccessData> =
        authenticationApi.refresh(RefreshRqDto(refreshToken = refresh, info = device.info))
            .map { it.toDomain() }

    override suspend fun blockRefresh(refresh: Token): Result<Unit> =
        authenticationApi.blockRefresh(BlockRefreshRqDto(refreshToken = refresh))
}