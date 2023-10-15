package ru.kyamshanov.mission.core.navigation.impl.domain

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import ru.kyamshanov.mission.core.navigation.api.ResultBus

internal class ResultBusImpl constructor(
    private val navigatorControllerHolder: NavigatorControllerHolder,
) : ResultBus {

    private val deferredMap = mutableMapOf<String, Channel<Any?>>()
    override val sharedFlow: SharedFlow<Pair<String, Any>> = MutableSharedFlow<Pair<String, Any>>().asSharedFlow()

    @Suppress("UNCHECKED_CAST")
    override fun <T> get(key: String, defaultValue: T): T =
        requireNotNull(navigatorControllerHolder.stackNavigation) { "StackNavigation controller cannot be null" }
            .let { controller ->
                TODO("Реализовать")
                /*controller.currentBackStackEntry?.savedStateHandle?.remove(key) ?: defaultValue*/
            }

    override suspend fun <T : Any?> awaitResult(key: String, defaultValue: T): T {
        val channel = Channel<T>(capacity = 1, onBufferOverflow = BufferOverflow.SUSPEND)
        return try {
            deferredMap[key] = channel as Channel<Any?>
            channel.receive().also { channel.close() }
        } catch (e: Exception) {
            //Log.e(TAG, "await exception", e)
            channel.close(e)
            defaultValue
        } finally {
            deferredMap.remove(key)
        }
    }

    override fun notify(key: String) {
        deferredMap[key]?.let { deferred ->
            requireNotNull(navigatorControllerHolder.stackNavigation) { "StackNavigation controller cannot be null" }
                .let { controller ->
                    //TODO Реализовать
                    null
                }
                .also { deferred.trySend(it) }
        }
    }

    private companion object {

        const val TAG = "ResultProviderImpl"
    }
}