package ru.kyamshanov.mission.components.main_screen.impl.ui.models

sealed interface SlimItem {

    val id: String
    val title: String
}

data class ProjectInfoSlim(override val id: String, override val title: String) : SlimItem

data class TaskInfoSlim(override val id: String, override val title: String) : SlimItem