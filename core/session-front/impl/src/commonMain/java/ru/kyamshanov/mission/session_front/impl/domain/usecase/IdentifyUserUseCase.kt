package ru.kyamshanov.mission.session_front.impl.domain.usecase

import ru.kyamshanov.mission.session_front.api.model.Token

internal interface IdentifyUserUseCase {

    suspend fun identify(): Result<Token>
}