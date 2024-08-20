package com.example.resources

import io.github.tabilzad.ktor.annotations.KtorFieldDescription
import io.ktor.resources.*

@Resource("/articles")
class Articles(
    @KtorFieldDescription("Articles sort order")
    val sort: String? = "new"
) {
    @Resource("new")
    class New(val parent: Articles = Articles())

    @Resource("{id}")
    class Id(
        val parent: Articles = Articles(),
        @KtorFieldDescription("Article identifier")
        val id: Long
    ) {
        @Resource("edit")
        class Edit(val parent: Id)
    }
}