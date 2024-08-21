package com.example

import com.example.routes.configureOpenApi
import com.example.routes.configureRegularRoutes
import com.example.routes.configureTypeSafeRoutes
import io.github.tabilzad.ktor.annotations.GenerateOpenApi
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*

fun main() {
    embeddedServer(
        factory = Netty,
        port = 8080,
        host = "0.0.0.0",
        module = Application::module
    ).start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation){
        json()
    }
    configureOpenApi()
    documentedModule()
}

@GenerateOpenApi
fun Application.documentedModule() {
    configureRegularRoutes()
    configureTypeSafeRoutes()
}
