package ru.kyamshanov.mission.core.navigation.common.utils

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext

val ComponentContext.retainedCoroutineScope: CoroutineScope get() = instanceKeeper.getOrCreate { RetainedCoroutine.Main() }

interface RetainedCoroutine : InstanceKeeper.Instance, CoroutineScope {
    override val coroutineContext: CoroutineContext
    override fun onDestroy() {
        cancel()
    }

    class Main : RetainedCoroutine {
        override val coroutineContext: CoroutineContext = SupervisorJob() + Dispatchers.Main.immediate
    }
}

