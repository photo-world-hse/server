plugins {
    id("photo.world.spring.infrastructure.lib")
}

dependencies {
    implementation(libs.aws.s3)

    implementation(project(":domain_images"))
}