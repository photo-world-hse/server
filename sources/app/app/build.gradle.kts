plugins {
    @Suppress("DSL_SCOPE_VIOLATION")
    alias(libs.plugins.spring.boot.plugin)
    id("photo.world.spring.lib")
}

dependencies {
    implementation(libs.spring.boot.starter)
    implementation(libs.spring.boot.starter.security)

    implementation(project(":app_security"))
}
