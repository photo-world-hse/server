plugins {
    id("photo.world.spring.web.lib")
}

dependencies {
    annotationProcessor(libs.spring.boot.configuration.processor)

    implementation(libs.bundles.jwt)
    implementation(libs.spring.boot.starter.security)
    implementation(libs.appache.commons.lang3)

    implementation(project(":domain_auth"))
    implementation(project(":domain_mail"))
    implementation(project(":web_user_service"))
}