plugins {
    `kotlin-dsl`
}

group = "photo.world.spring"

dependencies {
    implementation(libs.kotlin.gradle.plugin)
    implementation(project(":kotlin"))
    implementation(project(":utils"))
}

gradlePlugin {
    plugins.create("springLib") {
        id = "${group}.lib"
        displayName = "Spring libs"
        description = "The Gradle Plugin for setting up spring library"
        implementationClass = "${group}.SpringLibPlugin"
    }
    plugins.create("springWebLib") {
        id = "${group}.web.lib"
        displayName = "Spring web libs"
        description = "The Gradle Plugin for setting up spring web libraries"
        implementationClass = "${group}.SpringWebLibPlugin"
    }
    plugins.create("springInfrastructureLib") {
        id = "${group}.infrastructure.lib"
        displayName = "Spring Infrastructure libs"
        description = "The Gradle Plugin for setting up spring infrastructure libraries"
        implementationClass = "${group}.SpringInfrastructurePlugin"
    }
}
