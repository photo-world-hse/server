@file:Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.spring.boot.plugin) apply false
    alias(libs.plugins.spring.dependency.management) apply false
    alias(libs.plugins.kotlin.spring) apply false
    alias(libs.plugins.kotlin.jpa) apply false
}

subprojects {
    group = "photo.world"
    version = "0.0.1-SNAPSHOT"
}
