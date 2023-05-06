plugins {
    id("photo.world.spring.web.lib")
}

dependencies {
    implementation(libs.spring.boot.starter.security)

    implementation(project(":domain_images"))
}