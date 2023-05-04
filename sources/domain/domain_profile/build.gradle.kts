plugins {
    id("photo.world.kotlin.lib")
}

dependencies {
    implementation(project(":common_errors"))

    testImplementation(libs.bundles.kotest)
}