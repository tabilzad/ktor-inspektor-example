import io.github.tabilzad.ktor.model.SecurityScheme

plugins {
    kotlin("jvm") version "2.4.0"
    kotlin("plugin.serialization") version "2.4.0"
    id("io.ktor.plugin") version "3.5.0"
    id("io.github.tabilzad.inspektor") version "0.11.2-alpha"
}

group = "com.example"
version = "0.0.1"

swagger {
    documentation {
        // Document the servers the API is reachable on.
        servers = listOf("http://localhost:8080", "http://127.0.0.1:8080")

        info {
            title = "Example Ktor Server"
            description = "A showcase of every InspeKtor feature"
            version = "1.0.0"

            contact {
                name = "InspeKtor"
                url = "https://github.com/tabilzad/inspektor"
            }
            license {
                name = "Apache 2.0"
                url = "https://www.apache.org/licenses/LICENSE-2.0"
            }
        }

        // Infer response schemas directly from `call.respond(...)` — no annotations required.
        inferResponseSchemas = true

        // Resolve schema/field descriptions from KDoc comments.
        useKDocsForDescriptions = true

        // Discriminator property name used for sealed-class `oneOf` schemas.
        polymorphicDiscriminator = "type"

        // Map an opaque/third-party type to a primitive OpenAPI type.
        serialOverrides {
            typeOverride("java.time.Instant") {
                serializedAs = "string"
                format = "date-time"
                description = "ISO-8601 timestamp"
            }
        }

        // Global security requirements + reusable security schemes.
        security {
            scopes {
                or {
                    +"BasicAuth"
                    and {
                        "OpenId" to listOf("admin", "temp")
                    }
                }
            }
            schemes {
                "BasicAuth" to SecurityScheme(
                    type = "http",
                    scheme = "basic",
                    description = "Authenticate using basic username/password credentials"
                )
                "Bearer" to SecurityScheme(
                    type = "http",
                    scheme = "bearer",
                )
                "ApiKeyAuth" to SecurityScheme(
                    type = "apiKey",
                    `in` = "header",
                    name = "X-API-Key"
                )
            }
        }
    }

    pluginOptions {
        format = "yaml"
    }
}

application {
    mainClass.set("com.example.ApplicationKt")
    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.bundles.ktor)
    implementation(libs.logging)
}

// InspeKtor copies the generated spec into build/resources/main so it ships on the runtime classpath
// (Swagger UI serves it from there). That copy task writes into the jar tasks' input dir, so declare
// the dependency to satisfy Gradle's task-validation. (Tracked upstream — ideally the plugin wires
// this so consumers don't have to.)
tasks.withType<Jar>().configureEach {
    dependsOn(tasks.withType<Copy>().matching { it.name.startsWith("copyOpenApiSpec") })
}
