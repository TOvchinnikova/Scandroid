import com.t_ovchinnikova.android.scandroid_2.buildlogic.configureKotlin
import com.t_ovchinnikova.android.scandroid_2.buildlogic.configureKotlinAndroid
import com.t_ovchinnikova.android.scandroid_2.buildlogic.libraryExtension
import com.t_ovchinnikova.android.scandroid_2.buildlogic.libs
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidLibraryPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            pluginManager.run {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }

            requireNotNull(libraryExtension).apply {
                buildFeatures {
                    buildConfig = true
                }

                configureKotlin()
                configureKotlinAndroid(this)

                defaultConfig {
                    targetSdk = libs.findVersion("target-sdk").get().toString().toInt()

                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                    consumerProguardFiles ("consumer-rules.pro")
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