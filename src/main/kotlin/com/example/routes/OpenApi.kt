package com.example.routes

import io.ktor.server.application.*
import io.ktor.server.plugins.openapi.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.routing.*

fun Application.configureOpenApi() {
    routing {
        openAPI(path = "openapi", swaggerFile = "openapi/openapi.yaml")
        swaggerUI("swagger","openapi/openapi.yaml"){
            version = "5.17.12"
        }
    }
}
