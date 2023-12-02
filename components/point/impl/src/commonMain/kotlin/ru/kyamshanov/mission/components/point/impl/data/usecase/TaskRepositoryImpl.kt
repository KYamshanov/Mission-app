package ru.kyamshanov.mission.components.point.impl.data.usecase

import ru.kyamshanov.mission.components.point.impl.data.api.PointApi
import ru.kyamshanov.mission.components.point.impl.data.model.*
import ru.kyamshanov.mission.components.point.impl.data.model.AttachedTasksResponseDto
import ru.kyamshanov.mission.components.point.impl.data.model.TaskPriorityDto.*
import ru.kyamshanov.mission.components.point.impl.data.model.TaskStatusDto.*
import ru.kyamshanov.mission.components.points.api.common.TaskPriority
import ru.kyamshanov.mission.components.points.api.common.TaskSlim
import ru.kyamshanov.mission.components.points.api.common.TaskStatus
import ru.kyamshanov.mission.components.points.api.domain.TaskRepository

internal class TaskRepositoryImpl(
    private val pointApi: PointApi,
) : TaskRepository {

    override suspend fun getAll(): Result<List<TaskSlim>> = runCatching {
        pointApi.allTasks().toDomain()
    }

    override suspend fun search(phrase: String): Result<List<TaskSlim>> = runCatching {
        pointApi.search(phrase).toDomain()
    }

    override suspend fun setPosition(
        taskId: String,
        putBefore: String?,
        newTaskBefore: String?,
        oldBeforeTask: String?,
        oldAfterTask: String?,
    ): Result<Unit> =
        runCatching {
            pointApi.setPosition(
                RequestOrderTaskDto(
                    taskId = taskId,
                    putBefore = putBefore,
                    newTaskBefore = newTaskBefore,
                    oldBeforeTask = oldBeforeTask,
                    oldAfterTask = oldAfterTask
                )
            )
        }
}

private fun AttachedTasksResponseDto.toDomain(): List<TaskSlim> = this.items.map {
    TaskSlim(it.id, it.title, it.priority.toDomain(), it.status.toDomain(), type = it.type.toDomain(), offset = 0)
}

private fun TaskStatusDto.toDomain(): TaskStatus = when (this) {
    CREATED -> TaskStatus.CREATED
    IN_PROCESSING -> TaskStatus.IN_PROCESSING
    COMPLETED -> TaskStatus.COMPLETED
}

private fun TaskPriorityDto?.toDomain(): TaskPriority? = when (this) {
    PRIMARY -> TaskPriority.PRIMARY
    null -> null
    LOW -> TaskPriority.LOW
}
