plugins {
    @Suppress("DSL_SCOPE_VIOLATION")
    alias(libs.plugins.spring.boot.plugin)
    id("photo.world.spring.infrastructure.lib")
}

dependencies {
    implementation(libs.spring.boot.starter)
    implementation(libs.spring.boot.starter.security)
    implementation(libs.spring.boot.starter.webflux)

    implementation(project(":app_security"))
    implementation(project(":common_errors"))
    implementation(project(":common_profile"))

    implementation(project(":domain_profile"))
    implementation(project(":domain_auth"))
    implementation(project(":domain_images"))
    implementation(project(":domain_photosession"))

    implementation(project(":infrastructure_data"))
    implementation(project(":infrastructure_mail"))
    implementation(project(":infrastructure_images"))
    implementation(project(":infrastructure_sendbird"))

    implementation(project(":web_user_service"))
    implementation(project(":web_profile"))
    implementation(project(":web_images"))
    implementation(project(":web_photosession"))
}
