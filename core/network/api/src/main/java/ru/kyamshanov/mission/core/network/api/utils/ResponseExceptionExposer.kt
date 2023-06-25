package ru.kyamshanov.mission.core.network.api.utils

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import ru.kyamshanov.mission.core.network.api.model.CallException

@kotlin.jvm.Throws(CallException::class)
suspend inline fun <reified BodyType : Any?> HttpResponse.retrieveBody(): BodyType =
    if (status.isSuccess()) body()
    else throw CallException(status.value, "TODO")

