package ru.kyamshanov.mission.core.base.impl.domain

import dev.icerock.moko.resources.desc.Plural
import dev.icerock.moko.resources.desc.PluralFormatted
import dev.icerock.moko.resources.desc.Resource
import dev.icerock.moko.resources.desc.ResourceFormatted
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.provider.JsStringProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import ru.kyamshanov.mission.core.base.api.ResourcesProvider
import ru.kyamshanov.mission.core.ui.Res

internal actual class ResourcesProviderImpl actual constructor() : ResourcesProvider {

    private lateinit var strings: JsStringProvider

    init {
        CoroutineScope(Dispatchers.Main + SupervisorJob()).launch {
            Res.stringsLoader.getOrLoad()
        }
    }

    override fun getQuantityString(
        res: dev.icerock.moko.resources.PluralsResource,
        count: Int
    ): String = StringDesc.Plural(res, count).localized(strings)

    override fun getQuantityString(
        res: dev.icerock.moko.resources.PluralsResource,
        count: Int,
        vararg formatArgs: Any
    ): String = StringDesc.PluralFormatted(res, count, formatArgs).localized(strings)

    override fun getString(res: dev.icerock.moko.resources.StringResource): String =
        StringDesc.Resource(res).localized(strings)

    override fun getString(
        res: dev.icerock.moko.resources.StringResource,
        vararg formatArgs: Any
    ): String = StringDesc.ResourceFormatted(res, formatArgs).localized(strings)
}