package ru.kyamshanov

import io.ktor.server.application.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.httpsredirect.*
import ru.kyamshanov.plugins.configureRouting

fun main(args: Array<String>): Unit = EngineMain.main(args)


fun Application.module() {
    val resources = this.environment.config.propertyOrNull("server.resources")?.getString().orEmpty()
    val envSslPort = this.environment.config.property("ktor.deployment.sslPort").getString().toInt()
    configureRouting(resources)
    install(HttpsRedirect) {
        sslPort = envSslPort
        permanentRedirect = true
    }
}