package com.example.model

import io.github.tabilzad.ktor.annotations.KtorField
import io.github.tabilzad.ktor.annotations.KtorSchema
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.Instant

// Shows field-level docs via @KtorField and a class description via @KtorSchema (these replace the
// deprecated @KtorFieldDescription), plus nested refs, collections, maps, and a type mapped to a
// primitive via `serialOverrides` (see build.gradle.kts).
@Serializable
@KtorSchema(description = "Payload for creating a sample")
data class SampleRequest(
    @KtorField("An integer value")
    val int: Int,
    val string: String,
    val double: Double,
    @KtorField("Reference to another object")
    val objectRef: ObjectRef,
    @KtorField("A collection of strings")
    val list: List<String>,
    val setObjectRefs: Set<ObjectRef>,
    val map: Map<String, ObjectRef>,
    @Contextual
    @KtorField(description = "Created timestamp", format = "date-time")
    val instant: Instant,
)

@Serializable
data class ObjectRef(
    @KtorField("An integer value")
    val int: Int = 0,
    val list: List<String> = emptyList(),
)
