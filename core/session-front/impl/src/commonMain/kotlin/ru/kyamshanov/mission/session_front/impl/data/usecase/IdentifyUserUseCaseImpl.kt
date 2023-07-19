package ru.kyamshanov.mission.session_front.impl.data.usecase

import ru.kyamshanov.mission.session_front.api.model.Token
import ru.kyamshanov.mission.session_front.impl.data.api.IdentifyApi
import ru.kyamshanov.mission.session_front.impl.data.model.toDomain
import ru.kyamshanov.mission.session_front.impl.domain.usecase.IdentifyUserUseCase

internal class IdentifyUserUseCaseImpl constructor(
    private val identifyApi: IdentifyApi,
) : IdentifyUserUseCase {

    override suspend fun identify(): Result<Token> =
        identifyApi.identify().mapCatching { it.toDomain() }
}