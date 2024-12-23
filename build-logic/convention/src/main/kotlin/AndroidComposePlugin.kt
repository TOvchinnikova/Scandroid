import com.t_ovchinnikova.android.scandroid_2.buildlogic.commonExtension
import com.t_ovchinnikova.android.scandroid_2.buildlogic.configureAndroidCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply

class AndroidComposePlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "org.jetbrains.kotlin.plugin.compose")
            commonExtension.apply {
                configureAndroidCompose(this)
            }
        }
    }
}