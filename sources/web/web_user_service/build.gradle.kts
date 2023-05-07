plugins {
    id("photo.world.spring.web.lib")
}

dependencies {
    implementation(libs.spring.boot.starter.security)

    implementation(project(":common_errors"))
    implementation(project(":domain_auth"))
}