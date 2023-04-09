package photo.world.spring

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.PluginContainer
import photo.world.utils.implementationFromVersionCatalogIfExists
import photo.world.utils.runtimeOnlyFromVersionCatalogIfExists

private const val SpringBootStarterJpaDependency = "spring-boot-starter-jpa"
private const val JakartaPersistenceDependency = "jakarta-persistence"
private const val PostgreSqlDependency = "postgresql"

class SpringInfrastructurePlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.plugins.setUp()
        target.addDependencies()
    }

    private fun PluginContainer.setUp() {
        apply(SpringLibPlugin::class.java)
        apply("org.jetbrains.kotlin.plugin.jpa")
    }

    private fun Project.addDependencies() {
        implementationFromVersionCatalogIfExists(SpringBootStarterJpaDependency)
        implementationFromVersionCatalogIfExists(JakartaPersistenceDependency)
        runtimeOnlyFromVersionCatalogIfExists(PostgreSqlDependency)
    }
}
