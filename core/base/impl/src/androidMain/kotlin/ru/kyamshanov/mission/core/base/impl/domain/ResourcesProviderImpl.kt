package ru.kyamshanov.mission.core.base.impl.domain

import dev.icerock.moko.resources.PluralsResource
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.Plural
import dev.icerock.moko.resources.desc.PluralFormatted
import dev.icerock.moko.resources.desc.Resource
import dev.icerock.moko.resources.desc.ResourceFormatted
import dev.icerock.moko.resources.desc.StringDesc
import ru.kyamshanov.mission.core.base.api.ResourcesProvider
import ru.kyamshanov.mission.core.di.impl.Di
import ru.kyamshanov.mission.core.platform_base.di.PlatformBaseComponent

internal actual class ResourcesProviderImpl actual constructor() : ResourcesProvider {

    private val context =
        requireNotNull(Di.getComponent<PlatformBaseComponent>()).applicationContext


    override fun getQuantityString(
        res: PluralsResource,
        count: Int
    ): String = StringDesc.Plural(res, count).toString(context)

    override fun getQuantityString(
        res: PluralsResource,
        count: Int,
        vararg formatArgs: Any
    ): String = StringDesc.PluralFormatted(res, count, formatArgs).toString(context)

    override fun getString(res: StringResource): String =
        StringDesc.Resource(res).toString(context)

    override fun getString(
        res: StringResource,
        vararg formatArgs: Any
    ): String = StringDesc.ResourceFormatted(res, formatArgs).toString(context)
}