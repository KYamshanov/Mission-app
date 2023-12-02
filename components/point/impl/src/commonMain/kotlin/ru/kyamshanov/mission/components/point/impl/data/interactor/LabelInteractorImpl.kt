package ru.kyamshanov.mission.components.point.impl.data.interactor

import ru.kyamshanov.mission.components.point.impl.data.api.PointApi
import ru.kyamshanov.mission.components.point.impl.data.model.CreateProjectsRqDto
import ru.kyamshanov.mission.components.point.impl.data.model.toDomain
import ru.kyamshanov.mission.components.point.impl.domain.interactor.LabelInteractor
import ru.kyamshanov.mission.components.point.impl.domain.interactor.ProjectInteractor
import ru.kyamshanov.mission.components.point.impl.domain.models.LabelModel
import ru.kyamshanov.mission.components.point.impl.domain.models.ProjectModel
import ru.kyamshanov.mission.components.points.api.common.ProjectId

internal class LabelInteractorImpl(
    private val api: PointApi
) : LabelInteractor {

    override suspend fun getAll(): Result<List<LabelModel>> = runCatching {
        api.allLabels().items.map { it.toDomain() }
    }
}