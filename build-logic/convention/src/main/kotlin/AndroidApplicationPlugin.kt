import com.t_ovchinnikova.android.scandroid_2.buildlogic.applicationExtension
import com.t_ovchinnikova.android.scandroid_2.buildlogic.configureKotlinAndroid
import com.t_ovchinnikova.android.scandroid_2.buildlogic.configureKotlin
import com.t_ovchinnikova.android.scandroid_2.buildlogic.libs
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidApplicationPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            pluginManager.run {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
            }

            requireNotNull(applicationExtension).apply {
                buildFeatures {
                    buildConfig = true
                }

                configureKotlin()
                configureKotlinAndroid(this)

                defaultConfig {
                    targetSdk = libs.findVersion("target-sdk").get().toString().toInt()
                }

                buildTypes {
                    release {
                        isMinifyEnabled = true
                        proguardFiles (getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
                    }
                }
            }
        }
    }
}