package photo.world.utils

import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.implementation(notation: Any): Dependency? =
    add("implementation", notation)

fun DependencyHandler.annotationProcessor(notation: Any): Dependency? =
    add("annotationProcessor", notation)

fun DependencyHandler.runtimeOnly(notation: Any): Dependency? =
    add("runtimeOnly", notation)

fun DependencyHandler.testImplementation(notation: Any): Dependency? =
    add("testImplementation", notation)
