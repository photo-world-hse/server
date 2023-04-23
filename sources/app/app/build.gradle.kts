plugins {
    @Suppress("DSL_SCOPE_VIOLATION")
    alias(libs.plugins.spring.boot.plugin)
    id("photo.world.spring.infrastructure.lib")
}

dependencies {
    implementation(libs.spring.boot.starter)
    implementation(libs.spring.boot.starter.security)

    implementation(project(":app_security"))
    implementation(project(":web_user_service"))
    implementation(project(":data_user"))
    implementation(project(":domain_auth"))
    implementation(project(":infrastructure_mail"))
}
