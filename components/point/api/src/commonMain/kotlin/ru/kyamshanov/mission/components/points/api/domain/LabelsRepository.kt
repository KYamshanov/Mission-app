package ru.kyamshanov.mission.components.points.api.domain

import ru.kyamshanov.mission.components.points.api.common.LabelModel


interface LabelsRepository {

    suspend fun getAll(): Result<List<LabelModel>>
}