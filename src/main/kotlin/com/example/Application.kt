package com.example

import com.example.routes.articleRoutes
import com.example.routes.configureOpenApi
import com.example.routes.explicitResponseRoutes
import com.example.routes.inferredResponseRoutes
import com.example.routes.regularRoutes
import io.github.tabilzad.ktor.annotations.GenerateOpenApi
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.resources.*
import io.ktor.server.routing.*

fun main() {
    embeddedServer(
        factory = Netty,
        port = 8080,
        host = "0.0.0.0",
        module = Application::module,
    ).start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) { json() }
    install(Resources)

    // Serves the generated spec + Swagger UI (see OpenApi.kt).
    configureOpenApi()

    // The single entry point InspeKtor analyzes. It walks every route reachable from here — including
    // routes declared in the extension functions it calls — so feature demos live in separate files.
    documentedApi()
}

@GenerateOpenApi
fun Application.documentedApi() {
    routing {
        regularRoutes()
        inferredResponseRoutes()
        explicitResponseRoutes()
        articleRoutes()
    }
}
