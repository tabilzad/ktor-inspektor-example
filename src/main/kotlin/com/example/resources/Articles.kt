package com.example.resources

import io.github.tabilzad.ktor.annotations.KtorField
import io.ktor.resources.*

@Resource("/articles")
class Articles(
    @KtorField("Articles sort order")
    val sort: String? = "new",
) {
    @Resource("new")
    class New(val parent: Articles = Articles())

    @Resource("{id}")
    class Id(
        val parent: Articles = Articles(),
        @KtorField("Article identifier")
        val id: Long,
    ) {
        @Resource("edit")
        class Edit(val parent: Id)
    }
}
