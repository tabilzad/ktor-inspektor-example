package com.example.routes

import com.example.model.SampleErrorResponse
import com.example.model.SampleRequest
import com.example.model.SampleResponse
import io.github.tabilzad.ktor.annotations.KtorDescription
import io.github.tabilzad.ktor.annotations.KtorResponds
import io.github.tabilzad.ktor.annotations.ResponseEntry
import io.github.tabilzad.ktor.annotations.Tag
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

@Tag(["All Endpoints"])
fun Application.configureRegularRoutes() {
    routing {
        customerRoutes()
        orderRoutes()

        @Tag(["Shipments"])
        route("/shipments") {
            get {
                call.respond(SampleResponse())
            }
            post<SampleRequest> { request ->
                call.respond(SampleResponse.fromRequest(request))
            }
        }
    }
}

@Tag(["Customer"])
fun Route.customerRoutes() {
    @KtorResponds(
        mapping = [
            ResponseEntry("200", SampleResponse::class),
            ResponseEntry("400", SampleErrorResponse::class)
        ]
    )
    @KtorDescription(
        summary = "Get customer by id",
        description = "Returns customer by id"
    )
    get("/customer/{id}") {
        val idParam = call.parameters["id"]
        call.respond(HttpStatusCode.OK, SampleResponse()).also {
            println(idParam)
        }
    }

    @KtorResponds(mapping = [ResponseEntry("200", SampleResponse::class)])
    post("/customer") {
        val body = call.receive<SampleRequest>()
        call.respond(HttpStatusCode.OK, SampleResponse.fromRequest(body))
    }
}

@Tag(["Order"])
fun Route.orderRoutes() {
    route("/orders") {
        @KtorResponds(mapping = [ResponseEntry("200", SampleResponse::class, isCollection = true)])
        get("/all") {
            val query = call.request.queryParameters["sort"]
            call.respond(HttpStatusCode.OK, SampleResponse()).also {
                println(query)
            }
        }

        @KtorResponds(mapping = [ResponseEntry("200", SampleResponse::class)])
        get("/{id}") {
            val idParam = call.parameters["id"]
            call.respond(HttpStatusCode.OK, SampleResponse()).also {
                println(idParam)
            }
        }
    }
}
