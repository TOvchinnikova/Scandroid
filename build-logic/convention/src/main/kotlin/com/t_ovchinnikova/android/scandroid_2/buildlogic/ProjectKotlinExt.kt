package com.t_ovchinnikova.android.scandroid_2.buildlogic

import org.gradle.api.Project
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal fun Project.configureKotlin() {
    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = JavaLanguageVersion.of(libs.findVersion("jvm").get().toString()).toString()
        }
    }
}