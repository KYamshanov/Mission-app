package ru.kyamshanov.mission.components.point.impl.data.api

import ru.kyamshanov.mission.components.point.impl.data.model.*
import ru.kyamshanov.mission.components.point.impl.data.model.CreateTaskRequestDto
import ru.kyamshanov.mission.components.point.impl.data.model.CreateTaskResponseDto
import ru.kyamshanov.mission.components.points.api.common.TaskId

internal interface PointApi {

    suspend fun get(taskId: TaskId): GetTaskRsDto

    suspend fun allTasks(): AttachedTasksResponseDto

    suspend fun create(rq: CreateTaskRequestDto): CreateTaskResponseDto

    suspend fun delete(taskId: TaskId)

    suspend fun setType(taskId: TaskId, taskTypeDto: TaskTypeDto)

    suspend fun setStatus(taskId: TaskId, taskStatusDto: TaskStatusDto)

    suspend fun setPriority(taskId: TaskId, priority: TaskPriorityDto)

    suspend fun removePriority(taskId: TaskId)

    suspend fun search(phrase: String): AttachedTasksResponseDto

    suspend fun edit(data: EditTaskRsDto)
    suspend fun setPosition(body: RequestOrderTaskDto)

    suspend fun allProjects(): AllProjectsRsDto

    suspend fun createProject(rq: CreateProjectsRqDto): CreateProjectsRsDto

    suspend fun getProject(taskId: TaskId): GetProjectRsDto

    suspend fun allLabels(): LabelRsDto


    suspend fun setLabels(rq: SetLabelRqDto)

}