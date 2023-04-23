package photo.world.tests

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.tasks.TaskContainer
import org.gradle.api.tasks.TaskProvider

private const val TaskName = "runUnitTests"

class UnitTestsPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.registerUnitTestsTask()
    }

    private fun Project.registerUnitTestsTask() {
        val runUnitTestsTask = tasks.register(TaskName)
        val taskName = "test"
        tasks.safeConfigureProvider<Task>(taskName) {
            runUnitTestsTask.configure {
                this.dependsOn(this@safeConfigureProvider)
            }
        }
    }

    private inline fun <reified T : Task> TaskContainer.safeConfigureProvider(
        taskName: String,
        crossinline configAction: TaskProvider<T>.() -> Unit,
    ) {
        findByName(taskName)?.let { named(taskName, T::class.java).configAction() }
            ?: run { whenTaskAdded { if (name == taskName) named(taskName, T::class.java).configAction() } }
    }
}
