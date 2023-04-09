plugins {
    `kotlin-dsl`
    id("java-gradle-plugin")
}

group = "photo.world.tests"

dependencies {
    implementation(libs.kotlin.gradle.plugin)
    implementation(project(":utils"))
}

gradlePlugin {
    plugins.create("unit-tests") {
        id = "$group.unit"
        displayName = "Unit Tests"
        description = "The Gradle Plugin for configuring unit tests in project"
        implementationClass = "$group.UnitTestsPlugin"
    }
}
