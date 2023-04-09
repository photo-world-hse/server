package photo.world.spring

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.PluginContainer
import photo.world.kotlin.KotlinPlugin


class SpringLibPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.plugins.applySpringPlugins()
    }

    private fun PluginContainer.applySpringPlugins() {
        apply(KotlinPlugin::class.java)
        apply("org.jetbrains.kotlin.jvm")
        apply("io.spring.dependency-management")
        apply("org.jetbrains.kotlin.plugin.spring")
    }
}
