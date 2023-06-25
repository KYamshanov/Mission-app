package ru.kyamshanov.mission.core.base.impl.domain

import ru.kyamshanov.mission.core.base.api.ResourcesProvider

internal class ResourcesProviderImpl : ResourcesProvider {


    override fun getString(id: Int): String = TODO("Need implement")


    override fun getString(id: Int, vararg formatArgs: Any): String = TODO("Need implement")


    override fun getQuantityString(id: Int, count: Int): String = TODO("Need implement")


    override fun getQuantityString(id: Int, count: Int, vararg formatArgs: Any): String = TODO("Need implement")

}