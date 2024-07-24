package com.t_ovchinnikova.android.scandroid_2.buildlogic

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        buildFeatures {
            compose = true
        }

        composeOptions {
            kotlinCompilerExtensionVersion = libs.findVersion("androidxComposeCompiler").get().toString()
        }
    }

    dependencies {

        add("implementation", libs.findLibrary("compose-navigation").get())
        add("implementation", libs.findLibrary("compose-ui").get())
        add("implementation", libs.findLibrary("compose-constraint").get())
        add("implementation", libs.findLibrary("compose-material").get())
        add("implementation", libs.findLibrary("compose-uiToolingPreview").get())
        add("debugImplementation", libs.findLibrary("androidx-ui-tooling").get())
    }
}