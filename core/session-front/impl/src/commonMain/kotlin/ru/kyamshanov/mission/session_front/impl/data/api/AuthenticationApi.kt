package ru.kyamshanov.mission.session_front.impl.data.api

import ru.kyamshanov.mission.session_front.impl.data.model.BlockRefreshRqDto
import ru.kyamshanov.mission.session_front.impl.data.model.CheckAccessRqDto
import ru.kyamshanov.mission.session_front.impl.data.model.CheckAccessRsDto
import ru.kyamshanov.mission.session_front.impl.data.model.RefreshRqDto
import ru.kyamshanov.mission.session_front.impl.data.model.SessionTokensRsDto
import ru.kyamshanov.mission.session_front.impl.data.model.UserDto

internal interface AuthenticationApi {

    suspend fun login(rq: UserDto): Result<SessionTokensRsDto>

    suspend fun check(rq: CheckAccessRqDto): Result<CheckAccessRsDto>

    suspend fun refresh(rq: RefreshRqDto): Result<SessionTokensRsDto>

    suspend fun blockRefresh(rq: BlockRefreshRqDto): Result<Unit>
}