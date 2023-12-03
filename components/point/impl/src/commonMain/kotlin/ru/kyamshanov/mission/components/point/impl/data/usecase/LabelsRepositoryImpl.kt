package ru.kyamshanov.mission.components.point.impl.data.usecase

import ru.kyamshanov.mission.components.point.impl.data.api.PointApi
import ru.kyamshanov.mission.components.point.impl.data.model.RequestOrderTaskDto
import ru.kyamshanov.mission.components.point.impl.data.model.toDomain
import ru.kyamshanov.mission.components.points.api.common.LabelModel
import ru.kyamshanov.mission.components.points.api.common.TaskSlim
import ru.kyamshanov.mission.components.points.api.domain.LabelsRepository
import ru.kyamshanov.mission.components.points.api.domain.TaskRepository

internal class LabelsRepositoryImpl(
    private val pointApi: PointApi,
) : LabelsRepository {
    override suspend fun getAll(): Result<List<LabelModel>> = runCatching {
        pointApi.allLabels().items.map { it.toDomain() }
    }

}