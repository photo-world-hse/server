plugins {
    `kotlin-dsl`
}

group = "photo.world.kotlin"

dependencies {
    implementation(libs.kotlin.gradle.plugin)
    implementation(project(":utils"))
    implementation(project(":tests"))
}

gradlePlugin {
    plugins.create("kotlin") {
        id = "${group}.lib"
        displayName = "Pure Kotlin Library"
        description = "The Gradle Plugin for setting up pure kotlin library module"
        implementationClass = "${group}.KotlinPlugin"
    }
}
