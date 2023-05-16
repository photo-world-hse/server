plugins {
    id("photo.world.spring.infrastructure.lib")
}

dependencies {
    implementation(libs.spring.boot.starter.webflux)

    implementation(project(":domain_auth"))
    implementation(project(":domain_photosession"))
    implementation(project(":domain_profile"))
}