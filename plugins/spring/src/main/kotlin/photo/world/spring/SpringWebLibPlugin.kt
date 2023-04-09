package photo.world.spring

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.PluginContainer
import photo.world.kotlin.KotlinPlugin
import photo.world.utils.implementationFromVersionCatalogIfExists

private const val SpringBootStarterWebDependencyName = "spring-boot-starter-web"

class SpringWebLibPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.plugins.setUp()
        target.addDependencies()
    }

    private fun PluginContainer.setUp() {
        apply(SpringLibPlugin::class.java)
    }

    private fun Project.addDependencies() {
        implementationFromVersionCatalogIfExists(SpringBootStarterWebDependencyName)
    }
}
