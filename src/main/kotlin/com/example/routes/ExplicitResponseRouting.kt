package com.example.routes

import com.example.model.SampleErrorResponse
import com.example.model.SampleResponse
import io.github.tabilzad.ktor.annotations.KtorDescription
import io.github.tabilzad.ktor.annotations.KtorResponds
import io.github.tabilzad.ktor.annotations.ResponseEntry
import io.github.tabilzad.ktor.annotations.Tag
import io.github.tabilzad.ktor.annotations.responds
import io.github.tabilzad.ktor.annotations.respondsNothing
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

/**
 * Explicit response documentation. Use these when you want to document responses that aren't obvious
 * from the handler (extra status codes, error envelopes, collections). Explicit declarations always
 * take precedence over inference for the same status code; inference still fills in the rest.
 */
@Tag(["Explicit Responses"])
fun Route.explicitResponseRoutes() = route("/customers") {

    // @KtorResponds: declare every status code up front, with descriptions.
    @KtorDescription(
        summary = "Get a customer by id",
        description = "Returns the customer, or an error envelope when not found.",
    )
    @KtorResponds(
        mapping = [
            ResponseEntry("200", SampleResponse::class, description = "Customer found"),
            ResponseEntry("404", SampleErrorResponse::class, description = "Customer not found"),
        ],
    )
    get("/{id}") {
        call.respond(SampleResponse())
    }

    // @KtorResponds with isCollection = true documents an array response.
    @KtorResponds(
        mapping = [ResponseEntry("200", SampleResponse::class, isCollection = true)],
    )
    get {
        call.respond(listOf(SampleResponse()))
    }

    // responds<T>() DSL: inline, type-safe per-status declarations.
    @KtorDescription(summary = "Create a customer")
    post {
        responds<SampleResponse>(HttpStatusCode.Created, description = "Created")
        responds<SampleErrorResponse>(HttpStatusCode.Conflict, description = "Already exists")
        call.respond(HttpStatusCode.Created, SampleResponse())
    }

    // respondsNothing: a body-less response (e.g. 204 No Content).
    @KtorDescription(summary = "Delete a customer")
    delete("/{id}") {
        respondsNothing(HttpStatusCode.NoContent, description = "Deleted")
        responds<SampleErrorResponse>(HttpStatusCode.NotFound, description = "Customer not found")
        call.respond(HttpStatusCode.NoContent)
    }
}
