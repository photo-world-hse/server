package photo.world.kotlin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import photo.world.tests.ext.configureTests
import photo.world.utils.getTargetJvmVersion
import photo.world.utils.implementation
import photo.world.utils.implementationFromVersionCatalogIfExists

private const val JacksonDependencyName = "jackson"
private const val KotlinReflectDependencyName = "kotlin-reflect"

class KotlinPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.plugins.apply("kotlin")
        target.configureJvmTarget(target.getTargetJvmVersion().toString())
        target.configureTests()
        target.addDependencies()
    }

    private fun Project.addDependencies() {
        implementationFromVersionCatalogIfExists(JacksonDependencyName)
        implementationFromVersionCatalogIfExists(KotlinReflectDependencyName)
    }

    private fun Project.configureJvmTarget(version: String) {
        tasks.withType(KotlinCompile::class.java) {
            kotlinOptions {
                freeCompilerArgs = listOf("-Xjsr305=strict")
                jvmTarget = version
            }
        }
    }
}
