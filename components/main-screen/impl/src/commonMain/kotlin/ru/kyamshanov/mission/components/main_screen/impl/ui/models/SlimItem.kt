package ru.kyamshanov.mission.components.main_screen.impl.ui.models

sealed interface SlimItem {

    val id: String
    val title: String
    val isCompleted: Boolean
}

data class ProjectInfoSlim(override val id: String, override val title: String, override val isCompleted: Boolean) : SlimItem

data class TaskInfoSlim(override val id: String, override val title: String, override val isCompleted: Boolean) : SlimItem