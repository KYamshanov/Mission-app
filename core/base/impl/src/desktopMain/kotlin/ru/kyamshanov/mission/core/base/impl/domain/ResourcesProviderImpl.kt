package ru.kyamshanov.mission.core.base.impl.domain

import dev.icerock.moko.resources.PluralsResource
import ru.kyamshanov.mission.core.base.api.ResourcesProvider

internal actual class ResourcesProviderImpl actual constructor() : ResourcesProvider {

    override fun getQuantityString(
        res: PluralsResource,
        count: Int
    ): String = res.localized(quantity = count)

    override fun getQuantityString(
        res: PluralsResource,
        count: Int,
        vararg formatArgs: Any
    ): String = res.localized(quantity = count, args = formatArgs)

    override fun getString(res: dev.icerock.moko.resources.StringResource): String =
        res.localized()

    override fun getString(
        res: dev.icerock.moko.resources.StringResource,
        vararg formatArgs: Any
    ): String = res.localized(args = formatArgs)
}