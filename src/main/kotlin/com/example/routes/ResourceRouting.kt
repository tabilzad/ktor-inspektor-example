package com.example.routes

import com.example.resources.Articles
import io.github.tabilzad.ktor.annotations.KtorDescription
import io.github.tabilzad.ktor.annotations.Tag
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.resources.delete
import io.ktor.server.resources.get
import io.ktor.server.resources.post
import io.ktor.server.resources.put
import io.ktor.server.response.*
import io.ktor.server.routing.*

/**
 * Type-safe routing with Ktor [Resources]. Paths and parameters are derived from the `@Resource`
 * classes (see resources/Articles.kt); responses are inferred from the handlers. `Resources` is
 * installed in Application.module().
 */
@Tag(["Articles (type-safe)"])
fun Route.articleRoutes() {

    @KtorDescription(summary = "List articles", description = "Supports a `sort` query parameter.")
    get<Articles> { article ->
        call.respondText("List of articles sorted starting from ${article.sort}")
    }

    get<Articles.New> {
        call.respondText("Create a new article")
    }

    post<Articles> {
        call.respondText("An article is saved", status = HttpStatusCode.Created)
    }

    get<Articles.Id> { article ->
        call.respondText("An article with id ${article.id}")
    }

    get<Articles.Id.Edit> { article ->
        call.respondText("Edit an article with id ${article.parent.id}")
    }

    put<Articles.Id> { article ->
        call.respondText("An article with id ${article.id} updated")
    }

    delete<Articles.Id> { article ->
        call.respondText("An article with id ${article.id} deleted")
    }
}
