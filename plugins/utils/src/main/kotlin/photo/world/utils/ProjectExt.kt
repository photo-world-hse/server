package photo.world.utils

import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension

fun Project.getVersionCatalog(): VersionCatalog =
    extensions.getByType(VersionCatalogsExtension::class.java).named("libs")

fun Project.getTargetJvmVersion(): JavaVersion {
    val jvmTarget = getVersionCatalog().requireVersion("targetJvm")
    return JavaVersion.valueOf(jvmTarget)
}

fun Project.implementationFromVersionCatalogIfExists(name: String) {
    findLibraryInVersionCatalog(name) { notation ->
        dependencies.implementation(notation)
    }
}

fun Project.runtimeOnlyFromVersionCatalogIfExists(name: String) {
    findLibraryInVersionCatalog(name) { notation ->
        dependencies.runtimeOnly(notation)
    }
}

private inline fun Project.findLibraryInVersionCatalog(
    name: String,
    crossinline action: (notation: String) -> Unit,
) {
    getVersionCatalog().findLibrary(name).ifPresent { pluginProvider ->
        action(pluginProvider.get().toString())
    }
}
