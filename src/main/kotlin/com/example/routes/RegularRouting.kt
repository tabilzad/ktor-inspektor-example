package com.example.routes

import com.example.model.SampleRequest
import com.example.model.SampleResponse
import io.github.tabilzad.ktor.annotations.KtorDescription
import io.github.tabilzad.ktor.annotations.Tag
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

/**
 * The basics: path / query / header parameters, request bodies, endpoint descriptions, tags, and
 * hiding an endpoint. Tags (declared with [Tag]) group endpoints in the generated spec / Swagger UI.
 */
@Tag(["Basics"])
fun Route.regularRoutes() {

    @KtorDescription(
        summary = "Get an item",
        description = "Path, query and header parameters are detected automatically from the handler.",
    )
    get("/items/{id}") {
        val id = call.parameters["id"]                  // path parameter `{id}`
        val sort = call.request.queryParameters["sort"] // query parameter `?sort=`
        val apiKey = call.request.headers["X-API-Key"]  // header parameter
        call.respond(SampleResponse(string = "item $id sorted by $sort for $apiKey"))
    }

    @KtorDescription(summary = "Create an item")
    post("/items") {
        // The request body schema is inferred from `call.receive<T>()`.
        val request = call.receive<SampleRequest>()
        call.respond(HttpStatusCode.Created, SampleResponse.fromRequest(request))
    }
}
