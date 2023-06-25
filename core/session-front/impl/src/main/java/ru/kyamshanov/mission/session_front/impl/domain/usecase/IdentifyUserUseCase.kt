package ru.kyamshanov.mission.session_front.impl.domain.usecase

import ru.kyamshanov.mission.session_front.api.model.IdToken

internal interface IdentifyUserUseCase {

    suspend fun identify(): Result<IdToken>
}