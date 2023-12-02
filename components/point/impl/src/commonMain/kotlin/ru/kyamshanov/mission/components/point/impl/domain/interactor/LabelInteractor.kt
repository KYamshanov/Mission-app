package ru.kyamshanov.mission.components.point.impl.domain.interactor

import ru.kyamshanov.mission.components.point.impl.domain.models.LabelModel

internal interface LabelInteractor {

    suspend fun getAll(): Result<List<LabelModel>>
}