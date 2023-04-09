plugins {
    @Suppress("DSL_SCOPE_VIOLATION")
    alias(libs.plugins.spring.boot.plugin)
    id("photo.world.spring.lib")
}

dependencies {
    implementation(libs.spring.boot.starter)
}
