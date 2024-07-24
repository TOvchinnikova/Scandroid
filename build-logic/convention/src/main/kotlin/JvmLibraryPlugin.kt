import org.gradle.api.Plugin
import org.gradle.api.Project

class JvmLibraryPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        applyPlugins()
    }

    private fun Project.applyPlugins() {
        with(pluginManager) {
            apply("org.jetbrains.kotlin.jvm")
        }
    }
}
