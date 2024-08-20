package com.example.model

import io.github.tabilzad.ktor.annotations.KtorFieldDescription

@KtorFieldDescription("This is a request sample")
data class SampleRequest(
    @KtorFieldDescription("Integer")
    val int: Int,
    val string: String,
    val double: Double,
    @KtorFieldDescription("Reference to another object")
    val objectRef: ObjectRef,
    @KtorFieldDescription("Collection")
    val list: List<String>,
    val setObjectRefs: Set<ObjectRef>,
    val map: Map<String, ObjectRef>
)

data class ObjectRef(
    @KtorFieldDescription("Integer")
    val int: Int = 0,
    val list: List<String> = emptyList()
)
