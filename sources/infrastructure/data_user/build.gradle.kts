plugins {
    id("photo.world.spring.infrastructure.lib")
}

dependencies {
    implementation(libs.spring.boot.starter.security)

    implementation(project(":domain_auth"))
}