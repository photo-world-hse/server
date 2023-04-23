package photo.world.utils

import org.gradle.api.artifacts.VersionCatalog

fun VersionCatalog.requireVersion(alias: String): String = findVersion(alias).get().requiredVersion
