plugins {
    id("photo.world.spring.infrastructure.lib")
}

dependencies {
    implementation(libs.spring.boot.mail)

    implementation(project(":domain_mail"))
}