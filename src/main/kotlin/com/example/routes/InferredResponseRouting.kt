package com.example.routes

import com.example.model.OrderStatus
import com.example.model.Page
import com.example.model.PaymentMethod
import com.example.model.SampleErrorResponse
import com.example.model.SampleResponse
import com.example.model.UserId
import io.github.tabilzad.ktor.annotations.KtorDescription
import io.github.tabilzad.ktor.annotations.Tag
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

/**
 * Automatic response inference (`inferResponseSchemas = true`). None of these endpoints declare their
 * responses with `responds<T>()` or `@KtorResponds` — the status code, content type and body schema are
 * inferred directly from the `call.respond(...)` calls in the handler.
 */
@Tag(["Inferred Responses"])
fun Route.inferredResponseRoutes() = route("/inferred") {

    // Plain respond -> 200 + SampleResponse schema.
    @KtorDescription(summary = "Inferred 200")
    get("/sample") {
        call.respond(SampleResponse())
    }

    // The status is read from the HttpStatusCode argument -> 201.
    @KtorDescription(summary = "Inferred 201 from HttpStatusCode argument")
    post("/sample") {
        call.respond(HttpStatusCode.Created, SampleResponse())
    }

    // Multiple respond sites across branches -> multiple inferred responses (200 + 400).
    @KtorDescription(summary = "Inferred from both branches")
    get("/customers/{id}") {
        val id = call.parameters["id"]
        if (id.isNullOrBlank()) {
            call.respond(HttpStatusCode.BadRequest, SampleErrorResponse(400, "missing id"))
        } else {
            call.respond(SampleResponse())
        }
    }

    // Sealed type -> `oneOf` with a discriminator; each variant carries the pinned `type` property.
    @KtorDescription(summary = "Sealed (oneOf) response")
    get("/payment") {
        val payment: PaymentMethod = PaymentMethod.Card(last4 = "4242", brand = "VISA")
        call.respond(payment)
    }

    // Generic envelope -> Page<SampleResponse>.
    @KtorDescription(summary = "Generic paginated response")
    get("/page") {
        call.respond(Page(items = listOf(SampleResponse()), total = 1, page = 1, pageSize = 20))
    }

    // Value class -> unwrapped to its underlying `string` type.
    @KtorDescription(summary = "Value-class response")
    get("/user-id") {
        call.respond(UserId("u-123"))
    }

    // Enum -> string schema with an `enum` list.
    @KtorDescription(summary = "Enum response")
    get("/status") {
        call.respond(OrderStatus.SHIPPED)
    }

    // respondText -> text/plain content type with a string schema.
    @KtorDescription(summary = "Plain text response")
    get("/text") {
        call.respondText("pong")
    }
}
