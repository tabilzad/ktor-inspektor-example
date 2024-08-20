plugins {
    kotlin("jvm") version "2.0.0"
    kotlin("plugin.serialization") version "2.0.0"
    id("io.ktor.plugin") version "2.3.12"
    id("io.github.tabilzad.ktor-docs-plugin-gradle") version "0.6.2-alpha"
}

group = "com.example"
version = "0.0.1"

swagger {
    documentation {
        docsTitle = "Example Ktor Server"
        docsDescription = "Example Server Description"
        docsVersion = "1.0"
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
    implementation("io.swagger.codegen.v3:swagger-codegen-generators:1.0.36")
}
