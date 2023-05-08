plugins {
    id("photo.world.kotlin.lib")
}

dependencies {
    implementation(project(":common_errors"))
    api(project(":common_profile"))

    testImplementation(libs.bundles.kotest)
}