package ru.kyamshanov.mission.core.base.api

import dev.icerock.moko.resources.PluralsResource
import dev.icerock.moko.resources.StringResource

interface ResourcesProvider {

    fun getQuantityString(res: PluralsResource, count: Int): String
    fun getQuantityString(res: PluralsResource, count: Int, vararg formatArgs: Any): String

    fun getString(res: StringResource): String

    fun getString(res: StringResource, vararg formatArgs: Any): String
}