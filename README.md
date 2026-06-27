# InspeKtor Example

A small Ktor server that demonstrates **every feature** of
[InspeKtor](https://github.com/tabilzad/inspektor) — a Kotlin compiler plugin that generates an
OpenAPI (Swagger) specification from your Ktor routes at build time, with no runtime overhead.

## Run it

```bash
./gradlew run
```

Then open:

- **Swagger UI** → http://localhost:8080/swagger
- **OpenAPI plugin UI** → http://localhost:8080/openapi

The spec itself is generated during compilation to `build/openapi/openapi.yaml` (run `./gradlew build`).

## Versions

| Tool      | Version       |
|-----------|---------------|
| Kotlin    | 2.4.0         |
| Ktor      | 3.5.0         |
| InspeKtor | 0.11.2-alpha  |
| Gradle    | 8.14.4        |

> InspeKtor is a Kotlin **compiler** plugin, so the project's Kotlin version must match the one the
> plugin was built against (2.4.0 here).

## What each part demonstrates

Everything is enabled in `build.gradle.kts` under `swagger { … }` (servers, info, security schemes,
`serialOverrides`, `inferResponseSchemas`, `useKDocsForDescriptions`, `polymorphicDiscriminator`).

| Feature | Where |
|---------|-------|
| `@GenerateOpenApi` entry point + module wiring | [`Application.kt`](src/main/kotlin/com/example/Application.kt) |
| Path / query / header params, request bodies, `@KtorDescription`, `@Tag` | [`routes/RegularRouting.kt`](src/main/kotlin/com/example/routes/RegularRouting.kt) |
| **Automatic response inference** from `call.respond(...)` (no annotations) | [`routes/InferredResponseRouting.kt`](src/main/kotlin/com/example/routes/InferredResponseRouting.kt) |
| Explicit responses: `responds<T>()`, `@KtorResponds`, `respondsNothing` (override inference) | [`routes/ExplicitResponseRouting.kt`](src/main/kotlin/com/example/routes/ExplicitResponseRouting.kt) |
| Type-safe routing with Ktor `Resources` | [`routes/ResourceRouting.kt`](src/main/kotlin/com/example/routes/ResourceRouting.kt) + [`resources/Articles.kt`](src/main/kotlin/com/example/resources/Articles.kt) |
| `@KtorSchema` / `@KtorField` field docs, `@SerialName`, KDoc descriptions | [`model/SampleRequest.kt`](src/main/kotlin/com/example/model/SampleRequest.kt), [`model/SampleResponse.kt`](src/main/kotlin/com/example/model/SampleResponse.kt) |
| Sealed classes → `oneOf` + discriminator, value classes, generics, enums | [`model/AdvancedTypes.kt`](src/main/kotlin/com/example/model/AdvancedTypes.kt) |
| Security schemes + global security requirements, `serialOverrides` | [`build.gradle.kts`](build.gradle.kts) |
| Serving the spec + Swagger UI | [`routes/OpenApi.kt`](src/main/kotlin/com/example/routes/OpenApi.kt) |

### Automatic response inference (highlight)

With `inferResponseSchemas = true`, InspeKtor reads the response straight from the handler — status,
content type and body schema — so the common case needs no annotations:

```kotlin
get("/inferred/sample") {
    call.respond(SampleResponse())                    // -> 200, application/json, SampleResponse
}
post("/inferred/sample") {
    call.respond(HttpStatusCode.Created, SampleResponse())  // -> 201
}
```

It handles multiple branches, sealed (`oneOf`) types, value classes, generics, enums and `respondText`
(`text/plain`). Explicit `responds<T>()` / `@KtorResponds` always win per status code; inference fills
in the rest.
