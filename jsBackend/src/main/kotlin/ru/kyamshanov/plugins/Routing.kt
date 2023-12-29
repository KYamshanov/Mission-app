package ru.kyamshanov.plugins

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

fun Application.configureRouting(resources: String) {
    routing {
        get("/") {
            val file =
                File(resources + "index.html")
            call.respondFile(file)
        }
    }
    routing {
        get("/{file}") {
            val file =
                File(resources + call.parameters["file"])
            call.respondFile(file)
        }
    }
}
