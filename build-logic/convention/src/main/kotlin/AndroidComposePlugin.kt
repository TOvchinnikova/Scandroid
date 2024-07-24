import com.t_ovchinnikova.android.scandroid_2.buildlogic.commonExtension
import com.t_ovchinnikova.android.scandroid_2.buildlogic.configureAndroidCompose
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidComposePlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            commonExtension.apply {
                configureAndroidCompose(this)
            }
        }
    }
}