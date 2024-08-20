import java.net.URI

pluginManagement {
    repositories {
        // this is only to pull staged inspektor releases
        maven("https://s01.oss.sonatype.org/content/repositories/staging")
        mavenLocal()
        gradlePluginPortal()
    }
}

rootProject.name = "ktor-inspektor-example"
