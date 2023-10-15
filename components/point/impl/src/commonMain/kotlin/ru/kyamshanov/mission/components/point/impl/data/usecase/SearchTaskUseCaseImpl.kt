package ru.kyamshanov.mission.components.point.impl.data.usecase

import ru.kyamshanov.mission.components.point.impl.data.api.PointApi
import ru.kyamshanov.mission.components.point.impl.data.model.AttachedTasksResponseDto
import ru.kyamshanov.mission.components.point.impl.data.model.TaskPriorityDto
import ru.kyamshanov.mission.components.point.impl.data.model.TaskPriorityDto.*
import ru.kyamshanov.mission.components.point.impl.data.model.TaskStatusDto
import ru.kyamshanov.mission.components.point.impl.data.model.TaskStatusDto.*
import ru.kyamshanov.mission.components.points.api.common.TaskPriority
import ru.kyamshanov.mission.components.points.api.common.TaskSlim
import ru.kyamshanov.mission.components.points.api.common.TaskStatus
import ru.kyamshanov.mission.components.points.api.domain.SearchTaskUseCase

internal class SearchTaskUseCaseImpl(
    private val pointApi: PointApi,
) : SearchTaskUseCase {

    override suspend fun getAll(): Result<List<TaskSlim>> = runCatching {
        pointApi.allProjects().toDomain()
    }
}

private fun AttachedTasksResponseDto.toDomain(): List<TaskSlim> = this.items.map {
    TaskSlim(it.id, it.title, it.priority.toDomain(), it.status.toDomain())
}

private fun TaskStatusDto.toDomain(): TaskStatus = when (this) {
    CREATED -> TaskStatus.CREATED
    IN_PROCESSING -> TaskStatus.IN_PROCESSING
    COMPLETED -> TaskStatus.COMPLETED
}

private fun TaskPriorityDto?.toDomain(): TaskPriority? = when (this) {
    PRIMARY -> TaskPriority.PRIMARY
    null -> null
}
