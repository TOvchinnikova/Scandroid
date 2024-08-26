package com.t_ovchinnikova.android.scandroid_2.buildlogic

import org.gradle.api.Project
import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion

internal fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension<*, *, *, *, *>,
) {
    commonExtension.apply {
        compileSdk = libs.findVersion("compile-sdk").get().toString().toInt()

        defaultConfig {
            minSdk = libs.findVersion("min-sdk").get().toString().toInt()
        }

        compileOptions {
            val javaVersion = JavaVersion.toVersion(libs.findVersion("jvm").get())
            sourceCompatibility = javaVersion
            targetCompatibility = javaVersion
        }
    }
}

