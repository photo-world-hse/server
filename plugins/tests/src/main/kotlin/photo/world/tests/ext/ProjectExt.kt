package photo.world.tests.ext

import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import photo.world.utils.getVersionCatalog
import photo.world.utils.testImplementation

fun Project.configureTests() {
    val catalog = getVersionCatalog()
    with(dependencies) {
        catalog.findBundle("kotest").ifPresent(::testImplementation)
        catalog.findLibrary("junit").ifPresent(::testImplementation)
    }
    tasks.withType(Test::class.java) { useJUnitPlatform() }
}
