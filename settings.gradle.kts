rootProject.name = "photo-world"

pluginManagement {
    includeBuild("plugins")
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    plugins {
        `kotlin-dsl`
        id("org.jetbrains.kotlin.jvm") version "1.7.22"
    }
}
dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

apply(from = "gradle/project_configuration.groovy")
