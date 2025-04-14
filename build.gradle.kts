import io.github.tabilzad.ktor.model.SecurityScheme

plugins {
    kotlin("jvm") version "2.1.10"
    kotlin("plugin.serialization") version "2.1.10"
    id("io.ktor.plugin") version "3.1.1"
    id("io.github.tabilzad.inspektor") version "0.7.3-alpha"
}

group = "com.example"
version = "0.0.1"

swagger {
    documentation {
        servers = listOf("http://localhost:8080", "http://127.0.0.1:8080")
        info {
            title = "Example Ktor Server"
            description = "Example Server Description"
            version = "10"

            contact {
                name = "Inspektor"
                url = "https://github.com/tabilzad/inspektor"
            }
        }
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
    mavenLocal()
    mavenCentral()
    // this is only to pull staged inspektor releases
    maven("https://s01.oss.sonatype.org/content/repositories/staging")
}

dependencies {
    implementation(libs.bundles.ktor)
    implementation(libs.logging)
    implementation("io.swagger.codegen.v3:swagger-codegen-generators:1.0.36")
}
